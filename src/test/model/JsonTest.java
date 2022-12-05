package model;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Inspired by JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkAllList(LocationList locationList, String locationListName, ArrayList<Location> locationLists) {
        assertEquals(locationLists, locationList.getLocationList());
        assertEquals(locationListName, locationList.getLocationListName());
    }
}
