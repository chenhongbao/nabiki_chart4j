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
import java.util.Arrays;

public class BarChart extends PictureComponent{
    private final Series begin , end;

    public BarChart(Graphics2D g2d, int width, int height) {
        super(g2d, width, height);
        begin = new TimeSeries();
        end = new TimeSeries();
    }

    public BarChart(Graphics2D g2d, Series end, int width, int height) {
        super(g2d, width, height);
        this.begin = new TimeSeries();
        this.begin.setName("Default Bar Begin");
        this.begin.setSeries(
                new double[end.getCount()],
                Arrays.copyOf(end.getLabels(), end.getCount()));
        this.end = end;
    }

    public BarChart(Graphics2D g2d, Series begin, Series end, int width, int height) {
        super(g2d, width, height);
        this.begin = begin;
        this.end = end;
    }

    public Series getBegin() {
        return begin;
    }

    public Series getEnd() {
        return end;
    }

    private int getPixelY(double value, double coordMin, double coordMax) {
        Utils.checkValue(value, coordMin, coordMax);
        var raw = (coordMax - value) * getPictureSize().height / (coordMax - coordMin);
        return (int)Math.round(raw);
    }

    private double xNodeWidth() {
        return 1.0D * getPictureSize().width / end.getCount();
    }

    private double xPaintNodeWidth() {
        return xNodeWidth() * (1 - Styles.X_NODE_BLANK_PORTION);
    }

    private int getBegPixelX(int index) {
        var count = end.getCount();
        Utils.checkValue(index, 0, count - 1);
        return (int)Math.round(index * xNodeWidth());
    }

    private int getEndPixelX(int index) {
        return getBegPixelX(index) + (int)xPaintNodeWidth();
    }

    private double[] getYLabels() {
        var min = Math.min(this.begin.getMin(), this.end.getMin());
        var max = Math.max(this.begin.getMax(), this.end.getMax());
        if (end.getData().length < 1)
            throw new EmptySeriesException("no series data");
        return Utils.calculateLabels(min, max, Styles.Y_COOR_LABEL_COUNT);
    }

    private void paintBar(int index, double begin, double end, double coordMin, double coordMax) {
        var x = getBegPixelX(index);
        var y = Math.min(
                getPixelY(begin, coordMin, coordMax),
                getPixelY(end, coordMin, coordMax));
        var width = (int)xPaintNodeWidth();
        var height = Math.max(
                getPixelY(begin, coordMin, coordMax),
                getPixelY(end, coordMin, coordMax))
                - y;
        var oldColor = getColor();
        Color color;
        if (begin < end)
            color = Styles.BAR_UP_COLOR;
        else if (begin == end)
            color = Styles.BAR_FAIR_COLOR;
        else
            color = Styles.BAR_DOWN_COLOR;
        setColor(color);
        paintAndFillRect(x, y, width, height);
        setColor(oldColor);
    }

    private void paintZeroGrid(double coordMin, double coordMax) {
        if (coordMin * coordMax <= 0) {
            var zeroPixelY = getPixelY(0, coordMin, coordMax) + getMargin()[0];
            var oldColor = getColor();
            setColor(Styles.GRID_LINE_COLOR);
            drawLine(0, zeroPixelY, getSize().width, zeroPixelY);
            setColor(oldColor);
        }
    }

    @Override
    public void paint() {
        // Bar is painted on top of grid and coordinates, don't clear.
        var labels = getYLabels();
        paintZeroGrid(labels[0], labels[labels.length-1]);
        for (int i = 0; i < end.getCount(); ++i)
            paintBar(i, begin.getData()[i], end.getData()[i],
                    labels[0], labels[labels.length-1]);
    }
}
