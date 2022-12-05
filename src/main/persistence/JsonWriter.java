package persistence;

import java.io.*;
import java.util.ArrayList;

import model.LocationList;
import org.json.JSONArray;
import org.json.JSONObject;

//Inspired by JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //EFFECTS: constructs a writer to write into the destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: it opens the writer and throws FileNotFoundException is the destination file cannot be opened to write in
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECTS: writes the JSON version of allLists into the file
    public void write(ArrayList<LocationList> al) {
        JSONArray json = new JSONArray();
        for (LocationList l : al) {
            json.put(l.toJson());

        }
        saveToFile(json.toString(TAB));
    }


    //MODIFIES: this
    //EFFECTS: it closes the writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes given string to file
    public void saveToFile(String json) {
        writer.print(json);
    }
}
