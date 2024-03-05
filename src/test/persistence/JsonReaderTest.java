package persistence;

import model.Charity;
import model.CharityManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: JsonSerializationDemo
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CharityManager cm = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCharityManager() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCharityManager.json");
        try {
            CharityManager cm = reader.read();
            assertEquals(0, cm.getCharitiesSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCharityManager() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCharityManager.json");
        try {
            CharityManager cm = reader.read();
            ArrayList<Charity> charities = cm.getCharities();
            assertEquals(2, charities.size());
            checkCharity("turtles", 3000, 2000, charities.get(0));
            checkCharity("elephants", 5000, 0, charities.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}