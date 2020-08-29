package com.nabiki.chart4j.parts;

import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;

public class StickChart extends JPanel {
    private final Axis axis;

    public StickChart(Axis axis) {
        this.axis = axis;
        setup();
    }

    private void setup() {
        var dimension = new Dimension();
        dimension.height = axis.getPixelHeight();
        dimension.width = axis.getPixelWidth();
        super.setPreferredSize(dimension);
    }

    private void paintStick(int open, int high, int low, int close, int fromX,
                            int toX, Graphics2D g2d) {
        int x = fromX + (toX - fromX) / 2;
        var oldColor = g2d.getColor();
        if (close < open)
            g2d.setColor(Utils.RED);
        else if (close == open)
            g2d.setColor(oldColor);
        else
            g2d.setColor(Utils.GREEN);
        g2d.drawLine(x, high, x, low);
        g2d.drawLine(fromX, open, x, open);
        g2d.drawLine(x, close, toX, close);
        g2d.setColor(oldColor);
    }

    private void paintDashLine(Graphics2D g2d) {
        var oldColor = g2d.getColor();
        setForeground(Color.GRAY);
        var oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(1,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND,
                1.0f,
                new float[]{1f, 4f},
                2f));
        for (var label : axis.getVerticalLabels()) {
            var y = axis.y(label);
            g2d.drawLine(0, y, getSize().width, y);
        }
        g2d.setStroke(oldStroke);
        setForeground(oldColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Draw outline.
        g2d.drawLine(0, 0, 0, super.getSize().width);
        g2d.drawLine(0, 0, super.getSize().height, 0);
        if (axis.getCount() > 0) {
            // Interior dash lines.
            paintDashLine(g2d);
            for (int i = 0; i < axis.getCount(); ++i) {
                paintStick(
                        axis.y(axis.getOpen()[i]),
                        axis.y(axis.getHigh()[i]),
                        axis.y(axis.getLow()[i]),
                        axis.y(axis.getClose()[i]),
                        axis.fromX(i),
                        axis.toX(i), g2d);
            }
        }
    }
}
