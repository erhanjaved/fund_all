package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CharityManagerTest {

    private Charity testCharity1;
    private Charity testCharity2;
    private Charity testCharity3;

    private CharityManager testCharityManager;

    private ArrayList<Charity> al1;
    private ArrayList<Charity> al2;


    @BeforeEach
    void runBefore() {

        testCharity1 = new Charity("Turtles", 5000);
        testCharity2 = new Charity("Elephants", 8000);
        testCharity3 = new Charity("Pollution", 12000);

        testCharityManager = new CharityManager();

        al1 = new ArrayList<Charity>();
        al2 = new ArrayList<Charity>();

    }

    @Test
    void testConstructor() {
        assertEquals(0, testCharityManager.getCharitiesSize());
    }

    @Test
    void addCharity(){
        al1.add(testCharity1);
        testCharityManager.addCharity(testCharity1);
        assertEquals(1, testCharityManager.getCharitiesSize());
        assertEquals(al1, testCharityManager.getCharities());
        al1.add(testCharity2);
        testCharityManager.addCharity(testCharity2);
        assertEquals(2, testCharityManager.getCharitiesSize());
        assertEquals(al1, testCharityManager.getCharities());
    }

    @Test
    void countCharitiesWithReachedGoals() {
        testCharityManager.addCharity(testCharity1);
        testCharityManager.addCharity(testCharity2);
        testCharityManager.addCharity(testCharity3);
        testCharity1.addFunds(2000);
        assertEquals(0, testCharityManager.countCharitiesWithReachedGoals());
        testCharity1.addFunds(3000);
        assertEquals(1, testCharityManager.countCharitiesWithReachedGoals());
        testCharity2.addFunds(7000);
        testCharity3.addFunds(13000);
        assertEquals(2, testCharityManager.countCharitiesWithReachedGoals());
        testCharity2.addFunds(1000);
        assertEquals(3, testCharityManager.countCharitiesWithReachedGoals());
    }
}
