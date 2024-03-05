package persistence;

import model.Charity;
import model.CharityManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: JsonSerializationDemo
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            CharityManager cm = new CharityManager();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            CharityManager cm = new CharityManager();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCharityManager.json");
            writer.open();
            writer.write(cm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCharityManager.json");
            cm = reader.read();
            assertEquals(0, cm.getCharitiesSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            CharityManager cm = new CharityManager();
            Charity birds = new Charity("birds", 4000);
            Charity trees = new Charity("trees", 6000);
            trees.addFunds(2000);

            cm.addCharity(birds);
            cm.addCharity(trees);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCharityManager.json");
            writer.open();
            writer.write(cm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCharityManager.json");
            cm = reader.read();
            ArrayList<Charity> charities = cm.getCharities();
            assertEquals(2, charities.size());
            checkCharity("birds", 4000, 0, charities.get(0));
            checkCharity("trees", 6000, 2000, charities.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
