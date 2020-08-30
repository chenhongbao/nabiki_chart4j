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

public class XYGridTest {
    @Test
    public void basic() {
        var image = new BufferedImage(
                500,
                600,
                BufferedImage.TYPE_INT_ARGB);

        var series = new TimeSeries();
        series.setName("test series");
        series.setSeries(
                new double[] {4.5, 14.2, 9.15, 13, 23.5, 16.5},
                new String[] {"8月1日", "8月2日", "8月3日", "8月4日", "8月5日", "8月6日"});

        var grid = new XYGrid(image.createGraphics(), series, 200, 300);
        grid.setOffset(100, 200);
        grid.setMargin(10, 20, 30, 40);
        grid.paint();

        var yCoord = new YCoordinate(image.createGraphics(),series, 50, 300);
        yCoord.setOffset(300, 200);
        yCoord.setMargin(10, 0, 30, 0);
        yCoord.paint();

        var xCoord = new XCoordinate(image.createGraphics(),series, 200, 50);
        xCoord.setOffset(100, 500);
        xCoord.setMargin(0, 10, 0, 40);
        xCoord.paint();

        try {
            ImageIO.write(
                    image,
                    "png",
                    new File("C:\\Users\\chenh\\Desktop\\grid.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}