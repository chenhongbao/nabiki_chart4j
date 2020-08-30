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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DepthView extends PictureComponent {
    public static class DepthViewValue {
        public Object value = "";
        public Color color = Styles.FONT_FAIR_COLOR;

        public DepthViewValue(Object o, Color c) {
            value = o;
            color = c;
        }
    }

    private final Map<String, DepthViewValue> valueMap;

    DepthView(Graphics2D g2d, int width, int height) {
        super(g2d, width, height);
        valueMap = new ConcurrentHashMap<>();
    }

    DepthView(Graphics2D g2d, Map<String, DepthViewValue> valueMap, int width, int height) {
        super(g2d, width, height);
        this.valueMap = valueMap;
    }

    public Map<String, DepthViewValue> getValueMap() {
        return valueMap;
    }

    private int offsetY(int index) {
        return (index + 1) * (int)(getFont().getSize() * Styles.DEPTHVIEW_FONT_LINESEP);
    }

    private void paintKey(int index, String key) {
        var oldColor = getColor();
        setColor(Styles.FONT_FAIR_COLOR);
        paintString(key, 0, offsetY(index));
        setColor(oldColor);
    }

    private void paintValue(int index, DepthViewValue v, int offsetX) {
        var oldColor = getColor();
        if (v.color == null)
            setColor(Styles.FONT_FAIR_COLOR);
        else
            setColor(v.color);
        paintString(v.value.toString(), offsetX, offsetY(index));
        setColor(oldColor);
    }

    private void paintEntry(int index, String key, DepthViewValue value, int offsetX) {
        paintKey(index, key);
        paintValue(index, value, offsetX);
    }

    private void paintEntries() {
        var oldFont = getFont();
        // Set new font.
        var font = oldFont.deriveFont(Styles.DEPTHVIEW_FONT_SIZE);
        setFont(font);
        int maxKeyWidth = 0;
        for (var key : valueMap.keySet())
            maxKeyWidth = Math.max(maxKeyWidth, getStringWidth(key));
        var offsetX = maxKeyWidth + Styles.DEPTHVIEW_BLANK_WIDTH;
        int index = 0;
        for (var entry : valueMap.entrySet())
            paintEntry(index++, entry.getKey(), entry.getValue(), offsetX);
        // Restore old font.
        setFont(oldFont);
    }

    @Override
    public void paint() {
        clear();
        paintEntries();
    }
}
