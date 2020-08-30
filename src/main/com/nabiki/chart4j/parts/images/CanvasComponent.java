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

public abstract class CanvasComponent implements Canvas {
    private final Graphics2D g2d;
    private int offsetX = 0, offsetY = 0;
    private Color bgColor = Styles.CANVAS_BG_COLOR;
    private final Dimension dimension = new Dimension();

    protected CanvasComponent(Graphics2D g2d, int width, int height) {
        this.g2d = g2d;
        dimension.width = width;
        dimension.height = height;
    }

    @Override
    public void setOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public Dimension getSize() {
        return dimension;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        g2d.drawLine(getX(x1), getY(y1), getX(x2), getY(y2));
    }

    @Override
    public void drawString(String text, int x, int y) {
        g2d.drawString(text, getX(x), getY(y));
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        g2d.drawRect(getX(x), getY(y), width, height);
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        g2d.fillRect(getX(x), getY(y), width, height);
    }

    @Override
    public void clear() {
        var oldColor = getColor();
        setColor(bgColor);
        g2d.fillRect(
                getX(0),
                getY(0),
                getSize().width,
                getSize().height);
        setColor(oldColor);
    }

    @Override
    public void dispose() {
        g2d.dispose();
    }

    @Override
    public void setBackground(Color c) {
        bgColor = c;
    }

    @Override
    public Color getBackground() {
        return bgColor;
    }

    @Override
    public Stroke getStroke() {
        return g2d.getStroke();
    }

    @Override
    public void setStroke(Stroke stroke) {
        g2d.setStroke(stroke);
    }

    @Override
    public Color getColor() {
        return g2d.getColor();
    }

    @Override
    public void setColor(Color color) {
        g2d.setColor(color);
    }

    @Override
    public Font getFont() {
        return g2d.getFont();
    }

    @Override
    public void setFont(Font font) {
        g2d.setFont(font);
    }

    @Override
    public FontMetrics getFontMetrics(Font font) {
        return g2d.getFontMetrics(font);
    }

    @Override
    public int getStringWidth(String content) {
        var metrics = getFontMetrics(getFont());
        int width = 0;
        for (int i = 0; i < content.length(); i++) {
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

    private int getX(int imageX) {
        return offsetX + imageX;
    }

    private int getY(int imageY) {
        return offsetY + imageY;
    }
}
