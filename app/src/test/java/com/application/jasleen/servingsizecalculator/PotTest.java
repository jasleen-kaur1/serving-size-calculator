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
    /*
    // no need to ignore
    //@Ignore ("Throwing not yet defined for method") //NOT NEEDED??
    @Test (expected = IllegalArgumentException.class)
    public void testCreateThingsGoWrong() {
        potOne = new Pot("", 84);
        potOne.getName();
        potOne = new Pot("Saute Pan", -30000);
        potOne.getWeightInG();
    }
*/
/*
    @Test //Is this needed?
    public void testCreateThingsGoWrong() {
        try {
            potOne.setWeightInG(-500);
            fail();
        }catch(IllegalArgumentException e){
            //do nothing; should have triggered exception
            assertTrue(true);
        }
    }
*/
    @Test
    public void testCreateMoreObjects() {
        Pot potTwo = new Pot("Fry Pan", 1000);
        potOne.setName("Huge Pot");
        assertEquals("Huge Pot", potOne.getName());
        potTwo.setName("Gas Cooker");
        assertEquals("Gas Cooker", potTwo.getName());
    }

    @Test
    public void testCorrectSetName() {
        potOne.setName("Sauce Pan");
        assertEquals("Sauce Pan", potOne.getName());
        potOne.setName("S");
        assertEquals("S", potOne.getName());

    }

    @Test (expected = IllegalArgumentException.class) //not like one shown?
    public void testFailSetName () {
        potOne.setName("");
        potOne.setName(null);
    }

    @Test
    public void testCorrectSetWeightInG() {
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
    public void testZeroSetWeightInG() {
        potOne.setWeightInG(0);
    }

}