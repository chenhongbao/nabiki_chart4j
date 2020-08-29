package com.nabiki.chart4j.parts;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class PriceLabel extends JPanel {
    private final Axis axis;
    private final int tickLength = 5;
    private final Dimension dimension = new Dimension();

    PriceLabel(Axis axis, int width) {
        this.axis = axis;
        setup(width);
    }

    private void setup(int width) {
        dimension.height = axis.getPixelHeight();
        dimension.width = width;
        super.setPreferredSize(dimension);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        // Draw axis line.
        g2d.drawLine(0, 0, 0, super.getSize().height);
        if (axis.getCount() < 1)
            return;
        try {
            var fontOffset = getFont().getSize() / 2;
            for (var label : axis.getVerticalLabels()) {
                var y = axis.y(label);
                g2d.drawLine(0, y, tickLength, y);
                g2d.drawString(String.format("%.2f", label), tickLength * 2, y + fontOffset);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
