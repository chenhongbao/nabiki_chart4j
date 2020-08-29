package com.nabiki.chart4j.parts;

import org.junit.Test;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

public class PriceLabelTest {

    @Test
    public void basic() {
        JDialog dialog = new JDialog();

        Axis axis = new Axis();
        axis.setBlankPortion(0.33);
        axis.setPriceMax(64);
        axis.setPriceMin(23);
        axis.setPixelHeight(500);
        axis.setPixelMarginTop(5);
        axis.setPixelMarginBottom(15);

        // Calculate axis.
        axis.pack();

        PriceLabel label = new PriceLabel(axis, 50);
        label.setBackground(new Color(187, 154, 234));
        dialog.add(label);

        dialog.setSize(100, 600);
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