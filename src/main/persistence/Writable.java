package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

//Inspired by JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {
    JSONObject toJson();

}
