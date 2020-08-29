package com.nabiki.chart4j.parts;

import javax.swing.JPanel;
import java.awt.*;
import java.util.List;

public class StickFigure extends JPanel {
    private PriceLabel priceLabel;
    private DateTimeLabel dtLabel;
    private StickChart stick;
    private final int width, height;
    private final Axis axis = new Axis();

    public StickFigure(int width, int height) {
        this.width = width;
        this.height = height;
        setup();
    }

    private void setup() {
        axis.setPixelWidth(width - 50);
        axis.setPixelHeight(height - 30);
        axis.setPixelMarginTop(10);
        axis.setPixelMarginRight(10);
        axis.setPixelMarginBottom(10);
        axis.setPixelMarginLeft(10);
        axis.setBlankPortion(0.25);
        axis.pack();
        // Create chart elements.
        stick = new StickChart(axis);
        priceLabel = new PriceLabel(axis, 50);
        dtLabel = new DateTimeLabel(axis, 30);
        // Construct stick chart and price label.
        JPanel control0 = new JPanel();
        control0.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        control0.add(stick);
        control0.add(priceLabel);
        // Put them together.
        super.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        super.add(control0);
        super.add(dtLabel);
    }

    public void plot(List<Stick> sticks) {
        if (sticks == null || sticks.size() == 0)
            return;
        double[] open = new double[sticks.size()], high = new double[sticks.size()],
                low = new double[sticks.size()], close = new double[sticks.size()];
        double priceMax = -Double.MAX_VALUE, priceMin = Double.MAX_VALUE;
        String[] labels = new String[sticks.size()];
        for (int i = 0; i < sticks.size(); ++i) {
            open[i] = sticks.get(i).open;
            high[i] = sticks.get(i).high;
            low[i] = sticks.get(i).low;
            close[i] = sticks.get(i).close;
            priceMax = Math.max(priceMax, high[i]);
            priceMin = Math.min(priceMin, low[i]);
            labels[i] = sticks.get(i).label;
        }
        axis.setOpen(open);
        axis.setHigh(high);
        axis.setLow(low);
        axis.setClose(close);
        axis.setPriceMax(priceMax);
        axis.setPriceMin(priceMin);
        axis.setHorizontalLabels(labels);
        axis.pack();
        super.updateUI();
    }
}
