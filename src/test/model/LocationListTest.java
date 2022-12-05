package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationListTest {
    LocationList testList;
    Location l1;
    Location l2;
    Location l3;
    Location l4;
    Location l5;

    @BeforeEach
    void runBefore() {
        testList = new LocationList("TestList1");
        l1 = new Location("UBC", "4268 ABC Street", "V7N3H9", "Vancouver", "BC");
        l2 = new Location("Metrotown", "123 Main Street", "V3N9D4", "Vancouver", "BC");
        l3 = new Location("Costco", "321 Fraser Street", "V2N5K9", "Vancouver", "ON");
        l4 = new Location("Petsmart", "567 E 60 Ave", "V4N6D1", "Vancouver", "AB");
        l5 = new Location("Coco", "3700 Grandview Hwy", "V9J4G3", "Vancouver", "BC");
        testList.addLocation(l1);


    }
    @Test
    void testGetLocationListName(){
        assertEquals("TestList1", testList.getLocationListName());
        testList.setLocationListName("TestList2");
        assertEquals("TestList2", testList.getLocationListName());
    }

    @Test
    void testAddLocationToList() {
        assertEquals(1, testList.getLocationList().size());
        testList.addLocation(l2);
        testList.addLocation(l3);
        testList.addLocation(l4);
        testList.addLocation(l5);
        assertEquals(5, testList.getLocationList().size());
    }

    @Test
    void testRemoveLocationFromList() {
        testList.addLocation(l2);
        testList.addLocation(l3);
        testList.addLocation(l4);
        testList.addLocation(l5);
        assertEquals(5, testList.getLocationList().size());
        testList.removeLocation(l3);
        testList.removeLocation(l2);
        assertEquals(3, testList.getLocationList().size());

    }



    @Test
    void testToJson() {
        testList.addLocation(l2);
        assertEquals("TestList1", testList.toJson().get("locationListName"));
        assertEquals("[{\"streetName\":\"4268 ABC Street\",\"comments\":[],\"province\":\"BC\",\"city\":\"" +
                        "Vancouver\",\"postalCode\":\"V7N3H9\",\"name\":\"UBC\",\"rating\":20},{\"streetName\":\"123 " +
                        "Main Street\",\"comments\":[],\"province\":\"BC\",\"city\":\"Vancouver\",\"postalCode\"" +
                        ":\"V3N9D4\",\"name\":\"Metrotown\",\"rating\":20}]"
                , testList.toJson().get("list").toString());
    }




}
