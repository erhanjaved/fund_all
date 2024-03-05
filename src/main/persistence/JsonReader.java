package persistence;

import model.Charity;
import model.CharityManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// CITATION: JsonSerializationDemo
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads CharityManager from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CharityManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCharityManager(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses CharityManager from JSON object and returns it
    private CharityManager parseCharityManager(JSONObject jsonObject) {
        CharityManager cm = new CharityManager();
        addCharityManager(cm, jsonObject);
        return cm;
    }

    // MODIFIES: cm
    // EFFECTS: parses charities from JSON object and adds them to CharityManager
    private void addCharityManager(CharityManager cm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("charities");
        for (Object json : jsonArray) {
            JSONObject nextCharity = (JSONObject) json;
            addCharityInCharityManager(cm, nextCharity);
        }
    }

    // MODIFIES: cm
    // EFFECTS: parses charity from JSON object and adds it to CharityManager
    private void addCharityInCharityManager(CharityManager cm, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double fundingGoal = jsonObject.getDouble("funding goal");
        double currentFunds = jsonObject.getDouble("current funds");
        Charity charity = new Charity(name, fundingGoal);
        charity.setCurrentFunds(currentFunds);
        cm.addCharity(charity);
    }
}