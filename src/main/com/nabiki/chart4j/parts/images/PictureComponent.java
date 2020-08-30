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

public abstract class PictureComponent extends CanvasComponent implements Picture {
    private final int[] margin = new int[4];
    private final Dimension size = new Dimension();

    PictureComponent(Graphics2D g2d, int width, int height) {
        super(g2d, width, height);
        size.width = getSize().width;
        size.height = getSize().height;
    }

    @Override
    public void setMargin(int top, int right, int bottom, int left) {
        margin[0] = top;
        margin[1] = right;
        margin[2] = bottom;
        margin[3] = left;
        // Update size.
        size.width -= (left + right);
        size.height -= (top + bottom);
    }

    @Override
    public int[] getMargin() {
        return margin;
    }

    @Override
    public Dimension getPictureSize() {
        return size;
    }

    @Override
    public void paintLine(int x1, int y1, int x2, int y2) {
        drawLine(getX(x1), getY(y1), getX(x2), getY(y2));
    }

    @Override
    public void paintString(String text, int x, int y) {
        drawString(text, getX(x), getY(y));
    }

    @Override
    public void paintRect(int x, int y, int width, int height) {
        drawRect(getX(x), getY(y), width, height);
    }

    @Override
    public void paintAndFillRect(int x, int y, int width, int height) {
        fillRect(getX(x), getY(y), width, height);
    }

    private int getX(int graphX) {
        return margin[3] + graphX;
    }

    private int getY(int graphY) {
        return margin[0] + graphY;
    }
}
