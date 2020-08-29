package com.nabiki.chart4j.parts;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class DateTimeLabelTest {
    @Test
    public void basic() {
        JDialog dialog = new JDialog();

        Axis axis = new Axis();
        axis.setBlankPortion(0.33);
        axis.setHorizontalLabels(new String[] {"13:45", "14:00", "14:15", "14:30"});
        axis.setPixelWidth(600);
        axis.setPixelMarginRight(10);
        axis.setPixelMarginLeft(20);

        DateTimeLabel label = new DateTimeLabel(axis, 50);
        label.setBackground(new Color(187, 154, 234));
        dialog.add(label);

        dialog.setSize(600, 100);
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