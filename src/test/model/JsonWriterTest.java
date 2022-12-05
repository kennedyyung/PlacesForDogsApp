package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Inspired by JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ArrayList<LocationList> allLists = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testWriterEmptyTeam() {
        try {
            ArrayList<LocationList> allLists = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAllLists.json");
            writer.open();
            writer.write(allLists);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAllLists.json");
            allLists = reader.read();
            for (LocationList locationList : allLists) {
                assertEquals("my list", locationList.getLocationListName());
                assertEquals(0, locationList.getLocationList().size());
            }
        } catch (IOException e) {
            fail("Exception should not been thrown");
        }

    }

    @Test
    void testWriterAllLists() {
        try {
            ArrayList<LocationList> allLists = new ArrayList<>();
            Location ubc = new Location("UBC", "123 main st", "V3N4G2", "Vancouver", "BC");
            Location sfu = new Location("SFU", "123 apple st", "V6R3K2", "Burnaby", "BC");
//            locationList.addLocation(ubc);
//            allLists.add(l);
            JsonWriter writer = new JsonWriter("./data/testWriterAllLists.json");
            writer.open();
            writer.write(allLists);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterAllLists.json");
            allLists = reader.read();

            for (LocationList locationList : allLists) {
                locationList.addLocation(ubc);
                locationList.addLocation(sfu);
                assertEquals("Schools", locationList.getLocationListName());
                ArrayList<Location> locations = locationList.getLocationList();
                assertEquals(2, locations.size());
                checkAllList(locationList, "Schools", locations);
                for (Location l : locationList.getLocationList()) {
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

    @Test
    void testToJson() {
        ArrayList<LocationList> allLists = new ArrayList<>();
        Location ubc = new Location("UBC", "123 main st", "V3N4G2", "Vancouver", "BC");
        Location sfu = new Location("SFU", "123 apple st", "V6R3K2", "Burnaby", "BC");
        for (LocationList locationList : allLists) {
            sfu.getComments().add("Great place!");
            locationList.addLocation(ubc);
            locationList.addLocation(sfu);
           // assertEquals("Schools", locationList.getLocationListName());
            assertEquals("Schools", locationList.toJson().get("locationListName"));
           // assertEquals("SFU", locationList.toJson().get("locationListName"));
            assertEquals("Great place!", locationList.toJson().get("comments"));
            assertEquals("", locationList.toJson().get("comments"));


        }

    }
//    @Test
//    void testJson() {
//        ArrayList<LocationList> al = new ArrayList<>();
//        //LocationList locations = new LocationList("cafes");
//        for (LocationList locations : al) {
//            System.out.println(locations.getLocationListName());
////            assertEquals("cafes", locations.getLocationListName());
////            assertEquals("UBC", locations.toJson().get("locationListName"));
////            assertEquals("SFU", locations.toJson().get("locationListName"));
//        }
//    }


}
