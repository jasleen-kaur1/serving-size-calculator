package com.application.jasleen.servingsizecalculator;

import org.junit.Before;
import org.junit.Ignore;
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
    @Ignore ("Throwing not yet defined for method") //NOT NEEDED??
    @Test (expected = IllegalArgumentException.class)
    public void testCreateThingsGoWrong() {
        potOne = new Pot("", 84);
        potOne.getName();
        potOne = new Pot("Saute Pan", -30000);
        potOne.getWeightInG();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFailSetWeightInG() {
        potOne.setWeightInG(-8);
    }

    @Test
    public void testZeroSetWeightInG() {
        potOne.setWeightInG(0);
    }

    @Test
    public void testCorrectSetWeightInG() {
        potOne.setWeightInG(1);
        assertEquals(1, potOne.getWeightInG());
        potOne.setWeightInG(350);
        assertEquals(350, potOne.getWeightInG());
        potOne.setWeightInG(2147483647);
        assertEquals(2147483647, potOne.getWeightInG());

    }

    @Test (expected = IllegalArgumentException.class)
    public void testFailSetName () {
        potOne.setName("");
    }

    @Test
    public void testCorrectSetName() {
        potOne.setName("Sauce Pan");
        assertEquals("Sauce Pan", potOne.getName());
        potOne.setName("S");
        assertEquals("S", potOne.getName());

    }
    

    /*
    @Test (expected = IllegalArgumentException.class)
    public void setNullName() {
        potOne.setName(null);
    }
    */

}