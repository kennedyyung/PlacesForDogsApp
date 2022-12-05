package persistence;


import model.Location;
import model.LocationList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

//Inspired by JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads workroom from file then returns it. It also throws IOException when error occurs while reading data
    public ArrayList<LocationList> read() throws IOException {
        // List<List<Location>>
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseAllLists(jsonArray);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: parses allLists(ArrayList of LocationList) from JSON array and returns it
    private ArrayList<LocationList> parseAllLists(JSONArray jsonArray) {
        ArrayList<LocationList> al = new ArrayList<LocationList>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            addNewLocationList(al, jsonObject1);
        }


        return al;
    }

    //MODIFIES: al
    //EFFECTS: parses LocationList from JSON object and adds to al
    private void addNewLocationList(ArrayList<LocationList> al, JSONObject jsonObject) {
        JSONArray jsonLocationList = jsonObject.getJSONArray("list");
        String name = jsonObject.getString("locationListName");

        LocationList l = new LocationList(name);

        for (Object json : jsonLocationList) {
            JSONObject nextList = (JSONObject) json;
            addALocation(l, nextList);
        }

        al.add(l);


    }

    //MODIFIES: l
    //EFFECTS: parses Location from JSON object and adds it to l
    private void addALocation(LocationList l, JSONObject jsonObject) {

        String name = jsonObject.getString("name");
        String streetName = jsonObject.getString("streetName");
        String postalCode = jsonObject.getString("postalCode");
        String city = jsonObject.getString("city");
        String province = jsonObject.getString("province");
        int rating = jsonObject.getInt("rating");
        ArrayList<String> comments = new ArrayList<>();
        JSONArray commentsArray = jsonObject.getJSONArray("comments");
        Location location = new Location(name, streetName, postalCode, city, province, comments, rating);

        for (Object comments1 : commentsArray) {
            String newComment = comments1.toString();
            location.getComments().add(newComment);
        }

        l.addLocation(location);
    }

}
