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

import com.nabiki.chart4j.parts.exceptions.EmptySeriesException;

import java.awt.*;

public class XYGrid extends PictureComponent {
    protected final Series series;

    XYGrid(Graphics2D g2d, int width, int height) {
        super(g2d, width, height);
        series = new TimeSeries();
    }

    XYGrid(Graphics2D g2d, Series y, int width, int height) {
        super(g2d, width, height);
         series = y;
    }

    public Series getSeries() {
        return series;
    }

    @Override
    public void paint() {
        // Clear canvas before painting.
        clear();
        paintGrid();
        paintFrame();
    }

    private void paintFrame() {
        var size = getSize();
        gridLine(0,0, size.width, 0);
        gridLine(size.width, 0, size.width, size.height);
        gridLine(0, size.height, size.width, size.height);
        gridLine(0,0, 0, size.height);
    }

    private int getPixelY(double value, double coordMin, double coordMax) {
        Utils.checkValue(value, coordMin, coordMax);
        var raw = (coordMax - value) * getPictureSize().height / (coordMax - coordMin);
        return (int)Math.round(raw) + getMargin()[0];
    }

    private double xNodeWidth() {
        return 1.0D * getPictureSize().width / series.getCount();
    }

    private double xPaintNodeWidth() {
        return xNodeWidth() * (1 - Styles.X_NODE_BLANK_PORTION);
    }

    private int getBegPixelX(int index) {
        Utils.checkValue(index, 0, series.getCount() - 1);
        return (int)Math.round(index * xNodeWidth()) + getMargin()[3];
    }

    private void gridDashLine(int x1, int y1, int x2, int y2) {
        var oldColor = getColor();
        var oldStroke = getStroke();
        setColor(Styles.GRID_DASHLINE_COLOR);
        setStroke(Styles.DASHLINE_STROKE);
        drawLine(x1, y1, x2, y2);
        setColor(oldColor);
        setStroke(oldStroke);
    }

    private void gridLine(int x1, int y1, int x2, int y2) {
        var oldColor = getColor();
        setColor(Styles.GRID_LINE_COLOR);
        drawLine(x1, y1, x2, y2);
        setColor(oldColor);
    }

    private double[] getYLabels() {
        if (series.getData().length < 1)
            throw new EmptySeriesException("no series data");
        return Utils.calculateLabels(
                series.getMin(),
                series.getMax(),
                Styles.Y_COOR_LABEL_COUNT);
    }

    private void paintYGrid() {
        var size = getSize();
        var labels = getYLabels();
        for (var label : labels) {
            var pixelY = getPixelY(label, labels[0], labels[labels.length-1]);
            gridDashLine(0, pixelY, size.width, pixelY);
        }
    }

    private int[] getXLabelIndexes() {
        var labels = series.getLabels();
        int totalWidth = 0;
        for (var label : labels)
            totalWidth += getStringWidth(label);
        var size = getSize();
        var count = series.getCount();
        if (size.width > totalWidth) {
            var array = new int[count];
            for (int i = 0; i < count; ++ i)
                array[i] = i;
            return array;
        } else {
            var sampleRate = (int)Math.ceil(1.0D * totalWidth / size.width);
            var array = new int[(int)Math.floor(1.0D * (count - 1) / sampleRate) + 1];
            for (int i = 0; i < array.length; ++i)
                array[i] = i * sampleRate;
            return array;
        }
    }

    private void paintXGrid() {
        var size = getSize();
        var count = series.getCount();
        for (var index : getXLabelIndexes()) {
            var beg = getBegPixelX(index);
            var pixelX = beg + (int)Math.round(xPaintNodeWidth() / 2);
            gridDashLine(pixelX, 0, pixelX, size.height);
        }
    }

    private void paintGrid() {
        paintXGrid();
        paintYGrid();
    }
}
