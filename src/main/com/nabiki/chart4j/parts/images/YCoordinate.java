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

public class YCoordinate extends Coordinate {
    YCoordinate(Graphics2D g2d, int width, int height) {
        super(g2d, width, height);
    }

    YCoordinate(Graphics2D g2d, Series series, int width, int height) {
        super(g2d, series, width, height);
    }

    private double[] getYLabels() {
        if (series.getData().length < 1)
            throw new EmptySeriesException("no series data");
        return Utils.calculateLabels(
                series.getMin(),
                series.getMax(),
                Styles.Y_COOR_LABEL_COUNT);
    }

    private int getPixelY(double value, double min, double max) {
        Utils.checkValue(value, min, max);
        var raw = (max - value) * getPictureSize().height / (max - min);
        return (int)Math.round(raw);
    }

    @Override
    protected int[] getLabelX() {
        return new int[getYLabels().length];
    }

    @Override
    protected int[] getLabelY() {
        var labels = getYLabels();
        var array = new int[labels.length];
        for (int i = 0; i < labels.length; ++i)
            array[i] = getPixelY(labels[i], labels[0], labels[labels.length -1 ]);
        return array;
    }

    @Override
    protected String[] getCoordinateLabel() {
        var labels = getYLabels();
        var array = new String[labels.length];
        for (int i = 0; i < labels.length; ++i)
            array[i] = Double.toString(labels[i]);
        return array;
    }

    private void coorLine(int x1, int y1, int x2, int y2) {
        var oldColor = getColor();
        setColor(Styles.COOR_LINE_COLOR);
        paintLine(x1, y1, x2, y2);
        setColor(oldColor);
    }

    private void coorString(String text, int x, int y) {
        var oldColor = getColor();
        setColor(Styles.COOR_LABEL_COLOR);
        paintString(text, x, y);
        setColor(oldColor);
    }

    @Override
    protected void paintTick(int x, int y) {
        coorLine(x, y, x + Styles.COOR_TICK_LENGTH, y);
    }

    @Override
    protected void paintLabel(String text, int x, int y) {
        coorString(
                text,
                x + 2 * Styles.COOR_TICK_LENGTH,
                y + getFont().getSize() / 2);
    }
}
