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

import com.nabiki.chart4j.parts.exceptions.LabelDataNotMatchException;

import java.util.Objects;

public class TimeSeries implements Series {
    private double[] data = new double[0];
    private String[] labels = new String[0];
    private String name = "";
    private double min = 0, max = 1;

    private void setMinMax() {
        max = -Double.MAX_VALUE;
        min = Double.MAX_VALUE;
        for (var v : data) {
            max = Math.max(max, v);
            min = Math.min(min, v);
        }
    }

    @Override
    public void setSeries(double[] data, String[] labels) {
        Objects.requireNonNull(data, "null data array");
        Objects.requireNonNull(labels, "null label array");
        if (data.length == 0 || data.length != labels.length)
            throw new LabelDataNotMatchException(data.length + "!=" + labels.length);
        this.data = data;
        this.labels = labels;
        setMinMax();
    }

    @Override
    public double[] getData() {
        return data;
    }

    @Override
    public String[] getLabels() {
        return labels;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getMax() {
        return max;
    }

    @Override
    public double getMin() {
        return min;
    }

    @Override
    public int getCount() {
        return data.length;
    }
}
