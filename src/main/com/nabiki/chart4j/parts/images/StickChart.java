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

import com.nabiki.chart4j.parts.exceptions.DimensionNotMatchException;
import com.nabiki.chart4j.parts.exceptions.EmptySeriesException;

import java.awt.*;

public class StickChart extends PictureComponent {
    private final Series open, high, low, close;

    StickChart(Graphics2D g2d, int width, int height) {
        super(g2d, width, height);
        open = new TimeSeries();
        high = new TimeSeries();
        low = new TimeSeries();
        close = new TimeSeries();
    }

    StickChart(Graphics2D g2d, Series open, Series high, Series low, Series close,
               int width, int height) {
        super(g2d, width, height);
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public Series getOpen() {
        return open;
    }

    public Series getHigh() {
        return high;
    }

    public Series getLow() {
        return low;
    }

    public Series getClose() {
        return close;
    }

    private int getPixelY(double value, double coordMin, double coordMax) {
        Utils.checkValue(value, coordMin, coordMax);
        var raw = (coordMax - value) * getPictureSize().height / (coordMax - coordMin);
        return (int)Math.round(raw);
    }

    private double xNodeWidth() {
        return 1.0D * getPictureSize().width / open.getCount();
    }

    private double xPaintNodeWidth() {
        return xNodeWidth() * (1 - Styles.X_NODE_BLANK_PORTION);
    }

    private int getBegPixelX(int index) {
        Utils.checkValue(index, 0, open.getCount() - 1);
        return (int)Math.round(index * xNodeWidth());
    }

    private int getEndPixelX(int index) {
        return getBegPixelX(index) + (int)xPaintNodeWidth();
    }

    private double[] getYLabels() {
        if (open.getData().length < 1)
            throw new EmptySeriesException("no series data");
        return Utils.calculateLabels(
                low.getMin(),
                high.getMax(),
                Styles.Y_COOR_LABEL_COUNT);
    }

    private void paintStick(int index, double open, double high, double low, double close) {
        var labels = getYLabels();
        var begX = getBegPixelX(index);
        var endX = getEndPixelX(index);
        var midX = begX + (int)(xPaintNodeWidth() / 2);
        var pixelOpen = getPixelY(open, labels[0], labels[labels.length-1]);
        var pixelHigh = getPixelY(high, labels[0], labels[labels.length-1]);
        var pixelLow = getPixelY(low, labels[0], labels[labels.length-1]);
        var pixelClose = getPixelY(close, labels[0], labels[labels.length-1]);
        var oldColor = getColor();
        var oldStroke = getStroke();
        // Set up styles.
        Color color;
        if (open < close)
            color = Styles.STICK_UP_COLOR;
        else if (open == close)
            color = Styles.STICK_FAIR_COLOR;
        else
            color = Styles.STICK_DOWN_COLOR;
        setColor(color);
        setStroke(Styles.STICK_STROKE);
        paintLine(midX, pixelHigh, midX, pixelLow);
        paintLine(begX, pixelOpen, midX, pixelOpen);
        paintLine(midX, pixelClose, endX, pixelClose);
        // Restore styles.
        setColor(oldColor);
        setStroke(oldStroke);
    }

    @Override
    public void paint() {
        if (open.getCount() != high.getCount() || high.getCount() != low.getCount()
                || low.getCount() != close.getCount())
            throw new DimensionNotMatchException("stick dimension not matched");
        // Stick chart is painted on top of grid and coordinates, don't clear.
        for (int i = 0; i < open.getCount(); ++i)
            paintStick(
                    i,
                    open.getData()[i],
                    high.getData()[i],
                    low.getData()[i],
                    close.getData()[i]);
    }
}
