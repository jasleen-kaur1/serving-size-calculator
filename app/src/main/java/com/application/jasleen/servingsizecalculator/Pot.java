package com.application.jasleen.servingsizecalculator;

/**
 * Store information about a single pot
 */

public class Pot {
    private String name;
    private int weightInG;


    // Set member data based on parameters.
    public Pot( String name, int weightInG) {
        this.name = name;
        this.weightInG = weightInG;
    }

    // Return the weight
    public int getWeightInG() {
        return weightInG;
    }

    // Set the weight. Throws IllegalArgumentException if weight is less than 0.
    public void setWeightInG(int weightInG) {
        if (weightInG < 0){
            throw new IllegalArgumentException("Weight of pot must be >= 0");
        }
        this.weightInG = weightInG;
    }
    // Return the name.
    public String getName() {
        return name;
    }

    // Set the name. Throws IllegalArgumentException if name is an empty string (length 0),
    // or if name is a null-reference.
    public void setName(String name) {
        if (name == null || name.length()==0){
            throw new IllegalArgumentException("Pot must have a name of at least 1 character");
        }
        this.name = name;
    }


}
