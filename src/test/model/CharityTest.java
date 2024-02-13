package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharityTest {

    private Charity testCharity;

    @BeforeEach
    void runBefore() {
        testCharity = new Charity("Turtles", 5000);
    }

    @Test
    void testConstructor() {
        assertEquals("Turtles", testCharity.getName());
        assertEquals(5000, testCharity.getFundingGoal());
        assertEquals(0, testCharity.getCurrentFunds());
    }

    @Test
    void addFunds() {
        testCharity.addFunds(2000);
        assertEquals(2000, testCharity.getCurrentFunds());
        assertEquals(5000, testCharity.getFundingGoal());
    }

    @Test
    void hasReachedGoal(){
        assertFalse(testCharity.hasReachedGoal());
        testCharity.addFunds(2000);
        assertFalse(testCharity.hasReachedGoal());
        testCharity.addFunds(3000);
        assertTrue(testCharity.hasReachedGoal());
        testCharity.addFunds(1000);
        assertTrue(testCharity.hasReachedGoal());
    }

}
