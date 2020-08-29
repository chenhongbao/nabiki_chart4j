package com.nabiki.chart4j.parts;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {
    @Test
    public void basic() {
        var labels = Utils.getLabels(25, 134, 7);
        for ( double d : labels)
            System.out.print(d + " ");
    }
}