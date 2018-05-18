package com.application.jasleen.servingsizecalculator;

/**
 * Store information about a single pot
 */

public class Pot {
//IS THIS THE CORRECT FORMAT NEEDED?
    private String namePot; // ADDED
    private int weightPot; //ADDED

    // Set member data based on parameters.
    public Pot( String name, int weightInG) {
        namePot = name;
        weightPot = weightInG;
    }


    // Return the weight
    public int getWeightInG() {
        return weightPot;
    }

    // Set the weight. Throws IllegalArgumentException if weight is less than 0.
    public void setWeightInG(int weightInG) {
        if (weightInG < 0){
            throw new IllegalArgumentException(); // correctly used?
        }
        this.weightPot= weightInG; //???
    }

    // Return the name.
    public String getName() {
        return namePot;
    }

    // Set the name. Throws IllegalArgumentException if name is an empty string (length 0),
    // or if name is a null-reference.
    public void setName(String name) { //???
        if (name.length()==0){
            throw new IllegalArgumentException();
        }
        this.namePot = name;
    }


}
