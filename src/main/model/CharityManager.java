package model;

import java.util.ArrayList;

// Represents a list of charities
public class CharityManager {
    private ArrayList<Charity> charities;

    // EFFECTS: creates an instance of this class
    public CharityManager() {
        charities = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a charity to end of the list
    public void addCharity(Charity charity) {
        charities.add(charity);
    }

    // EFFECTS: returns the list of charities
    public ArrayList<Charity> getCharities() {
        return charities;
    }

    public int getCharitiesSize() {
        return charities.size();
    }


    // EFFECTS: returns number of charities that have reached their goals
    public int countCharitiesWithReachedGoals() {
        int count = 0;
        for (Charity charity : charities) {
            if (charity.hasReachedGoal()) {
                count++;
            }
        }
        return count;
    }

    public boolean isEmpty() {
        return charities.isEmpty();
    }

}
