package com.nabiki.chart4j.parts;

import java.awt.*;
import java.util.LinkedList;

public class Utils {
    public static double[] getLabels(double min, double max, int num) {
        double step = (max - min) / num;
        double temp = 0;
        // Normalize step into [0, 1).
        if (Math.pow(10, (int)Math.log10(step)) == step)
            temp = Math.pow(10, (int)(Math.log10(step)));
        else
            temp = Math.pow(10, (int)(Math.log10(step))+ 1);
        var tempStep = step / temp;
        var stdStep = 0.0D;
        if (0 <= tempStep && tempStep <= 0.1)
            stdStep = 0.1;
        else if (0.1 < tempStep && tempStep <= 0.2)
            stdStep = 0.2;
        else if (0.2 < tempStep && tempStep <= 0.25)
            stdStep = 0.25;
        else if (0.25 < tempStep && tempStep <= 0.5)
            stdStep = 0.5;
        else
            stdStep = 1.0;
        // Recover step from [0, 1).
        var finalStep = stdStep * temp;
        double beg, end;
        beg = Math.floor(min / finalStep) * finalStep;
        end = Math.ceil(max / finalStep) * finalStep;
        // Create return array.
        var r = new LinkedList<Double>();
        while (beg <= end) {
            r.add(beg);
            beg += finalStep;
        }
        var array = new double[r.size()];
        for (int i = 0; i < array.length; ++i)
            array[i] = r.get(i);
        return array;
    }

    public static Color RED = new Color(197, 34, 25);
    public static Color GREY = new Color(125, 125, 125);
    public static Color GREEN = new Color(34, 154, 24);
    public static Color BLUE = new Color(25, 25, 165);
}
