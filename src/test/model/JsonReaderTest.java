package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Inspired by JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader( "./data/noSuchFile.txt");
        try {
            ArrayList<LocationList> allLists = reader.read();
            fail("IO Exception expected");
        } catch (IOException e) {

        }
    }

    @Test
    void testReaderEmptyAllLists() {
    JsonReader reader = new JsonReader("./data/emptyAllLists.json");
    try {
        ArrayList<LocationList> allLists = reader.read();
        for(LocationList locationList : allLists) {
            assertEquals("my list", locationList.getLocationListName());
            assertEquals(0, locationList.getLocationList().size());
        }
    } catch (IOException e) {
        fail("Couldn't read from file");
    }
    }

    @Test
    void testReaderAllLists() {
        JsonReader reader = new JsonReader("./data/allLists.json");
        try {
            ArrayList<LocationList> allLists = reader.read();
            for (LocationList locationList : allLists) {
                assertEquals("Schools", locationList.getLocationListName());
                ArrayList<Location> locations = locationList.getLocationList();
                assertEquals(1, locations.size());
                for(Location l : locationList.getLocationList()) {
                    assertEquals("123 main st", l.getStreetName());
                    assertEquals("BC", l.getProvince());
                    assertEquals("Vancouver", l.getCity());
                    assertEquals("V5N3H2", l.getPostalCode());
                    assertEquals("Has good latte", l.getComments().get(0));
                    assertEquals(8, l.getRating());
                }
            }
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
