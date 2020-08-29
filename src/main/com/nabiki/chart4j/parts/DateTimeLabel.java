package com.nabiki.chart4j.parts;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

public class DateTimeLabel extends JPanel {
    private final Axis axis;
    private final int tickLength = 5;
    private final Dimension dimension = new Dimension();

    public DateTimeLabel(Axis axis, int height) {
        this.axis = axis;
        setup(height);
    }

    private void setup(int height) {
        dimension.height = height;
        dimension.width = axis.getPixelWidth();
        super.setPreferredSize(dimension);
    }

    private int getStringWidth(String content) {
        var metrics = getFontMetrics(getFont());
        int width = 0;
        for (int i = 0; i < content.length(); i++) {
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

    private Set<Integer> getLabelIndexes() {
        // Get total length of all x labels.
        int totalWidth = 0;
        for (var label : axis.getHorizontalLabels())
            totalWidth += getStringWidth(label);
        int step = 1;
        if (totalWidth >= axis.getFitPixelWidth())
            step = totalWidth / axis.getPixelWidth();
        var r = new HashSet<Integer>();
        int index = 0;
        while (index < axis.getCount()) {
            r.add(index);
            index += step;
        }
        return r;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        // Draw axis line.
        g2d.drawLine(0, 0, super.getSize().width, 0);
        if (axis.getCount() < 1)
            return;
        try {
            var fontOffset = getFont().getSize();
            var labelIndexes = getLabelIndexes();
            for (int i = 0; i < axis.getCount(); ++i) {
                int x = axis.fromX(i) + (axis.toX(i) - axis.fromX(i)) / 2;
                g2d.drawLine(x, 0, x, tickLength);
                if (labelIndexes.contains(i)) {
                    var str = axis.getHorizontalLabels()[i];
                    var offset = getStringWidth(str) / 2;
                    g2d.drawString(str, x - offset, tickLength + fontOffset);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
