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

public class StickChartTest {
    Series extractGridSeries(Series high, Series low) {
        if (high.getCount() < 2)
            return high;
        double[] values = new double[high.getCount()];
        for (int i = 0; i < values.length; ++i)
            values[i] = high.getMin();
        values[0] = high.getMax();
        values[1] = low.getMin();
        String[] labels = Arrays.copyOf(high.getLabels(), high.getCount());
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

        var open = new TimeSeries();
        open.setName("open");
        open.setSeries(
                new double[] {4.5, 5.4, 14.2, 13, 11.5, 9.5},
                new String[] {"8月1日", "8月2日", "8月3日", "8月4日", "8月5日", "8月6日"});

        var high = new TimeSeries();
        high.setName("high");
        high.setSeries(
                new double[] {7.5, 14.2, 15.3, 13, 11.5, 16.5},
                new String[] {"8月1日", "8月2日", "8月3日", "8月4日", "8月5日", "8月6日"});

        var low = new TimeSeries();
        low.setName("low");
        low.setSeries(
                new double[] {3.5, 5.2, 9.15, 11, 8.5, 9.5},
                new String[] {"8月1日", "8月2日", "8月3日", "8月4日", "8月5日", "8月6日"});

        var close = new TimeSeries();
        close.setName("close");
        close.setSeries(
                new double[] {5.5, 14.2, 14.3, 11.2, 9.5, 16.5},
                new String[] {"8月1日", "8月2日", "8月3日", "8月4日", "8月5日", "8月6日"});

        var gridSeries = extractGridSeries(high, low);
        var grid = new XYGrid(image.createGraphics(), gridSeries, 200, 300);
        grid.setOffset(100, 200);
        grid.setMargin(10, 20, 30, 40);
        grid.paint();

        var yCoord = new YCoordinate(image.createGraphics(), gridSeries, 50, 300);
        yCoord.setOffset(301, 200);
        yCoord.setMargin(10, 0, 30, 0);
        yCoord.paint();

        var xCoord = new XCoordinate(image.createGraphics(), gridSeries, 200, 50);
        xCoord.setOffset(100, 501);
        xCoord.setMargin(0, 20, 0, 40);
        xCoord.paint();

        var stickChart = new StickChart(image.createGraphics(), open, high, low, close, 200, 300);
        stickChart.setOffset(100, 200);
        stickChart.setMargin(10, 20, 30, 40);
        stickChart.paint();

        try {
            ImageIO.write(
                    image,
                    "png",
                    new File("C:\\Users\\chenh\\Desktop\\stick_chart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}