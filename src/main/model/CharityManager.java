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
        EventLog.getInstance().logEvent(new Event("Charity added: " + charity.getName()));
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
        EventLog.getInstance().logEvent(new Event("Viewed charities with reached goals"));
        return count;
    }

    // REQUIRES: 1< choice < charities.getCharitiesSize()
    // MODIFIES: this
    // EFFECTS: removes the specified numbered charity in the list
    public void removeCharity(int choice) {
        Charity removedCharity = charities.get(choice - 1);
        charities.remove(choice - 1);
        EventLog.getInstance().logEvent(new Event("Charity " + removedCharity.getName() + " removed."));
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
