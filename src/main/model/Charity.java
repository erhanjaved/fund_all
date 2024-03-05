package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a charity having a name, funding goal (in dollars) and current funds (in dollars)
public class Charity implements Writable {
    private String name;
    private double fundingGoal;
    private double currentFunds;

    /*
     * REQUIRES: charityName has a non-zero length
     * EFFECTS: name of charity is set to charityName;
     *          the charity's funding goal is set to fundingGoal
     */
    public Charity(String charityName, double fundingGoal) {
        this.name = charityName;
        this.fundingGoal = fundingGoal;
        this.currentFunds = 0;
    }

    public String getName() {
        return name;
    }

    public double getFundingGoal() {
        return fundingGoal;
    }

    public double getCurrentFunds() {
        return currentFunds;
    }

    public void setCurrentFunds(double funds) {
        currentFunds = funds;
    }

    /*
     * REQUIRES: amount >= 0
     * MODIFIES: this
     * EFFECTS: amount is added to currentFunds
     */
    public void addFunds(double amount) {
        currentFunds += amount;
    }

    /*
     * EFFECTS: returns true if charity has reached/exceeded its funding goal
     */
    public boolean hasReachedGoal() {
        return currentFunds >= fundingGoal;
    }

    // CITATION: JsonSerializationDemo
    // URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("funding goal", fundingGoal);
        json.put("current funds", currentFunds);
        return json;
    }
}

