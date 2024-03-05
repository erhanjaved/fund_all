package persistence;

import model.Charity;
import model.CharityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

// CITATION: JsonSerializationDemo
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkCharity(String name, double fundingGoal, double currentFunds, Charity charity) {
        assertEquals(name, charity.getName());
        assertEquals(fundingGoal, charity.getFundingGoal());
        assertEquals(currentFunds, charity.getCurrentFunds());
    }
}
