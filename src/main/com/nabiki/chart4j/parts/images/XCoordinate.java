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

import java.awt.*;

public class XCoordinate extends Coordinate {
    XCoordinate(Graphics2D g2d, int width, int height) {
        super(g2d, width, height);
    }

    XCoordinate(Graphics2D g2d, Series series, int width, int height) {
        super(g2d, series, width, height);
    }

    private double xNodeWidth(int count) {
        return 1.0D * getPictureSize().width / count;
    }

    private double xPaintNodeWidth(int count) {
        return xNodeWidth(count) * (1 - Styles.X_NODE_BLANK_PORTION);
    }

    private int getBegPixelX(int index, int count) {
        Utils.checkValue(index, 0, count - 1);
        var w = xNodeWidth(count);
        return (int)Math.round(index * w);
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

    @Override
    protected int[] getLabelX() {
        var indexes = getXLabelIndexes();
        var count = series.getCount();
        var array = new int[indexes.length];
        for (int i = 0; i < array.length; ++i)
            array[i] = (int)Math.round(xPaintNodeWidth(count) / 2)
                    + getBegPixelX(indexes[i], count);
        return array;
    }

    @Override
    protected int[] getLabelY() {
        return new int[getXLabelIndexes().length];
    }

    @Override
    protected String[] getCoordinateLabel() {
        var indexes = getXLabelIndexes();
        String[] array = new String[indexes.length];
        for (int i = 0; i < array.length; ++i)
            array[i] = series.getLabels()[indexes[i]];
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
        coorLine(x, y, x, y + Styles.COOR_TICK_LENGTH);
    }

    @Override
    protected void paintLabel(String text, int x, int y) {
        coorString(
                text,
                x - getStringWidth(text) / 2,
                y + getFont().getSize() + Styles.COOR_TICK_LENGTH);
    }
}
