package com.application.jasleen.servingsizecalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage a collection of pots.
 */
public class PotCollection {
    private List<Pot> pots = new ArrayList<>(); // Pot is type of object to be stored in ArrayList

    public void addPot(Pot pot) { //adds pot to the list
        pots.add(pot);
    }

    public void changePot(Pot pot, int indexOfPotEditing) {
        validateIndexWithException(indexOfPotEditing);
        pots.remove(indexOfPotEditing);
        pots.add(indexOfPotEditing, pot);
    }

    public int countPots() { //count the total number of pots there are
        return pots.size();
    }

    public Pot getPot(int index) {
        validateIndexWithException(index);
        return pots.get(index);
    }

    // Useful for integrating with an ArrayAdapter
    public String[] getPotDescriptions() { //shown on screen
        String[] descriptions = new String[countPots()];
        for (int i = 0; i < countPots(); i++) {
            Pot pot = getPot(i);
            descriptions[i] = pot.getName() + " - " + pot.getWeightInG() + "g";
        }
        return descriptions;
    }

    private void validateIndexWithException(int index) {
        if (index < 0 || index >= countPots()) {
            throw new IllegalArgumentException();
        }

    }

    //added for Optional Feature 4.3 to Delete Pot
    public void deletePot(int indexOfPotEditing ){
        pots.remove(indexOfPotEditing);
    }

}
