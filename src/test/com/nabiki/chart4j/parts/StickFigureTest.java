package com.nabiki.chart4j.parts;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class StickFigureTest {
    @Test
    public void none() {
        JDialog dialog = new JDialog();
        JPanel chartControl = new StickFigure(500, 600);

        dialog.add(chartControl);
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

    @Test
    public void figure() {
        JDialog dialog = new JDialog();
        StickFigure figure = new StickFigure(500, 600);

        dialog.add(figure);
        dialog.setSize(600, 700);
        dialog.setVisible(true);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.dispose();
            }
        });

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        figure.plot(getSticks());

        try {
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<Stick> getSticks() {
        List<Stick> sticks = new LinkedList<>();

        Stick stick;

        stick= new Stick();
        stick.open = 14;
        stick.high = 25;
        stick.low = 13;
        stick.close = 22;
        stick.label = "14:35";
        sticks.add(stick);

        stick= new Stick();
        stick.open = 22;
        stick.high = 24;
        stick.low = 10;
        stick.close = 23;
        stick.label = "14:36";
        sticks.add(stick);

        stick= new Stick();
        stick.open = 23;
        stick.high = 30;
        stick.low = 22;
        stick.close = 28;
        stick.label = "14:37";
        sticks.add(stick);

        stick= new Stick();
        stick.open = 27;
        stick.high = 33;
        stick.low = 24;
        stick.close = 24;
        stick.label = "14:38";
        sticks.add(stick);

        stick= new Stick();
        stick.open = 25;
        stick.high = 27;
        stick.low = 17;
        stick.close = 17;
        stick.label = "14:39";
        sticks.add(stick);

        stick= new Stick();
        stick.open = 17;
        stick.high = 17;
        stick.low = 9;
        stick.close = 11;
        stick.label = "14:40";
        sticks.add(stick);

        stick= new Stick();
        stick.open = 10;
        stick.high = 18;
        stick.low = 8;
        stick.close = 17;
        stick.label = "14:41";
        sticks.add(stick);

        return sticks;
    }
}
