package com.application.jasleen.servingsizecalculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PotTest {
    private Pot potOne;

    @Before
    public void setUp() throws Exception {
        potOne = new Pot("Cooker", 400);
    }

    @Test
    public void testCreate() {
        assertEquals(400, potOne.getWeightInG());
        assertEquals("Cooker", potOne.getName());

    }

    @Test
    public void testCorrectSetName() {
        potOne.setName("Sauce Pan");
        assertEquals("Sauce Pan", potOne.getName());
        potOne.setName("S");
        assertEquals("S", potOne.getName());

    }

    @Test (expected = IllegalArgumentException.class)
    public void testFailSetName () {
        potOne.setName("");
        //assertEquals("Cooker", potOne.getName());
        potOne.setName(null);
        //assertEquals("Cooker", potOne.getName());
    }

    @Test
    public void testCorrectSetWeightInG() {
        potOne.setWeightInG(0);
        assertEquals(0, potOne.getWeightInG());
        potOne.setWeightInG(1);
        assertEquals(1, potOne.getWeightInG());
        potOne.setWeightInG(350);
        assertEquals(350, potOne.getWeightInG());
        potOne.setWeightInG(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, potOne.getWeightInG());

    }

    @Test (expected = IllegalArgumentException.class)
    public void testFailSetWeightInG() {
        potOne.setWeightInG(-1);
        potOne.setWeightInG(-100);
        potOne.setWeightInG(Integer.MIN_VALUE);
    }

    @Test
    public void testCreateMoreObjects() {
        Pot potTwo = new Pot("Fry Pan", 1000);
        potOne.setName("Huge Pot");
        potOne.setWeightInG(600);
        assertEquals("Huge Pot", potOne.getName());
        assertEquals(600, potOne.getWeightInG());
        potTwo.setName("Gas Cooker");
        potTwo.setWeightInG(200);
        assertEquals("Gas Cooker", potTwo.getName());
        assertEquals(200, potTwo.getWeightInG());
    }

}