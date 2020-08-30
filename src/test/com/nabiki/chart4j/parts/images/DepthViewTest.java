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

public class DepthViewTest {
    @Test
    public void basic() {
        var image = new BufferedImage(
                500,
                500,
                BufferedImage.TYPE_INT_RGB);

        var depthView = new DepthView(
                image.createGraphics(),
                200,
                200);
        var m = depthView.getValueMap();
        m.put("合约代码", new DepthView.DepthViewValue("c2101", null));
        m.put("开盘", new DepthView.DepthViewValue(2267.0D, null));
        m.put("收盘", new DepthView.DepthViewValue(2268.0F, Styles.FONT_UP_COLOR));
        m.put("结算", new DepthView.DepthViewValue(2263.0F, Styles.FONT_DOWN_COLOR));

        depthView.setMargin(10, 10, 10, 10);
        depthView.setOffset(100, 100);

        depthView.paint();

        try {
            ImageIO.write(
                    image,
                    "png",
                    new File("C:\\Users\\chenh\\Desktop\\depth_view.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}