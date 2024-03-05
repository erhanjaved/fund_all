package model;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import persistence.Writable;

// Represents a list of charities
public class CharityManager implements Writable {
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

    // CITATION: JsonSerializationDemo
    // URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("charities", charitiesToJson());
        return json;
    }

    // EFFECTS: returns things in this CharityManager as a JSON array
    // CITATION: JsonSerializationDemo
    // URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private JSONArray charitiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Charity c : charities) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

}
