/*
 * Copyright (c) 2020 Hongbao Chen <chenhongbao@outlook.com>
 *
 * Licensed under the  GNU Affero General Public License v3.0 and you may not use
 * this file except in compliance with the  License. You may obtain a copy of the
 * License at
 *
 *                    https://www.gnu.org/licenses/agpl-3.0.txt
 *
 * Permission is hereby  granted, free of charge, to any  person obtaining a copy
 * of this software and associated  documentation files (the "Software"), to deal
 * in the Software  without restriction, including without  limitation the rights
 * to  use, copy,  modify, merge,  publish, distribute,  sublicense, and/or  sell
 * copies  of  the Software,  and  to  permit persons  to  whom  the Software  is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE  IS PROVIDED "AS  IS", WITHOUT WARRANTY  OF ANY KIND,  EXPRESS OR
 * IMPLIED,  INCLUDING BUT  NOT  LIMITED TO  THE  WARRANTIES OF  MERCHANTABILITY,
 * FITNESS FOR  A PARTICULAR PURPOSE AND  NONINFRINGEMENT. IN NO EVENT  SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS  BE  LIABLE FOR  ANY  CLAIM,  DAMAGES OR  OTHER
 * LIABILITY, WHETHER IN AN ACTION OF  CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE  OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.nabiki.chart4j.parts.images;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class BarChartTest {
    Series extractGridSeries(Series begin, Series end) {
        if (begin.getCount() < 2)
            return begin;
        double[] values = new double[begin.getCount()];
        for (int i = 0; i < values.length; ++i)
            values[i] = begin.getMin();
        values[0] = Math.max(begin.getMax(), end.getMax());
        values[1] = Math.min(begin.getMin(), end.getMin());
        String[] labels = Arrays.copyOf(begin.getLabels(), begin.getCount());
        var grid = new TimeSeries();
        grid.setName("grid");
        grid.setSeries(values, labels);
        return grid;
    }

    @Test
    public void basic() {
        var image = new BufferedImage(
                500,
                600,
                BufferedImage.TYPE_INT_RGB);

        var begin = new TimeSeries();
        begin.setName("begin");
        begin.setSeries(
                new double[] {0, 0, 0, 0, 0, 0},
                new String[] {"8月1日", "8月2日", "8月3日", "8月4日", "8月5日", "8月6日"});

        var end = new TimeSeries();
        end.setName("end");
        end.setSeries(
                new double[] {5.5, -5.4, 14.3, 11.2, -9.5, 16.5},
                new String[] {"8月1日", "8月2日", "8月3日", "8月4日", "8月5日", "8月6日"});

        var gridSeries = extractGridSeries(begin, end);
        var grid = new XYGrid(image.createGraphics(), gridSeries, 200, 200);
        grid.setOffset(100, 100);
        grid.setMargin(10, 20, 30, 40);
        grid.paint();

        var yCoord = new YCoordinate(image.createGraphics(), gridSeries, 50, 200);
        yCoord.setOffset(301, 100);
        yCoord.setMargin(10, 0, 30, 0);
        yCoord.paint();

        var xCoord = new XCoordinate(image.createGraphics(), gridSeries, 200, 50);
        xCoord.setOffset(100, 301);
        xCoord.setMargin(0, 20, 0, 40);
        xCoord.paint();

        var barChart = new BarChart(image.createGraphics(), begin, end, 200, 200);
        barChart.setOffset(100, 100);
        barChart.setMargin(10, 20, 30, 40);
        barChart.paint();

        try {
            ImageIO.write(
                    image,
                    "png",
                    new File("C:\\Users\\chenh\\Desktop\\bar_chart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}