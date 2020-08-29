package com.nabiki.chart4j.parts;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class StickChartTest {

    @Test
    public void basic() {
        JDialog dialog = new JDialog();

        Axis axis = new Axis();
        axis.setBlankPortion(0.33);
        axis.setPriceMax(33);
        axis.setPriceMin(10);
        axis.setPixelWidth(500);
        axis.setPixelHeight(600);
        axis.setPixelMarginTop(5);
        axis.setPixelMarginRight(10);
        axis.setPixelMarginBottom(15);
        axis.setPixelMarginLeft(20);

        // Set sticks.
        axis.setOpen(new double[] {10, 25,32, 17});
        axis.setHigh(new double[] {14, 32,33, 17});
        axis.setLow(new double[] {10, 14,23, 13});
        axis.setClose(new double[] {13, 19,28, 15});
        axis.setHorizontalLabels(new String[] {"13:45", "14:00", "14:15", "14:30"});

        // Calculate axis.
        axis.pack();

        StickChart chart = new StickChart(axis);
        chart.setBackground(new Color(187, 154, 234));
        dialog.add(chart);

        dialog.setSize(600, 700);
        dialog.setVisible(true);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.dispose();
            }
        });

        try {
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}