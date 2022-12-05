package model;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    Location l1;
    Location l2;
    Location l3;
    Location l4;
    Location l5;

    @BeforeEach
    void runBefore(){

        l1 = new Location("UBC", "4268 ABC Street", "V7N3H9", "Vancouver", "BC");
        l2 = new Location("Metrotown", "123 Main Street", "V3N9D4", "Vancouver", "BC");
        l3 = new Location("Costco", "321 Fraser Street", "V2N5K9", "Vancouver", "ON");
        l4 = new Location("Petsmart", "567 E 60 Ave", "V4N6D1", "Vancouver", "AB");
        l5 = new Location("Coco", "3700 Grandview Hwy", "V9J4G3", "Vancouver", "BC");


    }

    @Test
    void testFullAddress() {
        assertEquals(l1.fullAddress(), "4268 ABC Street, V7N3H9, Vancouver, BC");
            }

    @Test
    void testGetName(){
        assertEquals("UBC", l1.getName());
        l1.setName("SFU");
        assertEquals("SFU", l1.getName());
    }
    @Test
    void testGetStreetName(){
        assertEquals("4268 ABC Street", l1.getStreetName());
        l1.setStreetName("151 W 7th Ave");
        assertEquals("151 W 7th Ave", l1.getStreetName());
    }

    @Test
    void testGetPostalCode(){
        assertEquals("V7N3H9", l1.getPostalCode());
        l1.setPostalCode("V1J2K3");
        assertEquals("V1J2K3", l1.getPostalCode());
    }

    @Test
    void testGetCity() {
        assertEquals("Vancouver", l1.getCity());
        l1.setCity("Burnaby");
        assertEquals("Burnaby", l1.getCity());
    }

    @Test
    void testGetProvince() {
        assertEquals("BC", l1.getProvince());
        l1.setProvince("ON");
        assertEquals("ON", l1.getProvince());
    }


    @Test
    void testGetRating(){
        assertEquals(20, l1.getRating());
        l1.setRating(5);
        assertEquals(5, l1.getRating());
    }

    @Test
    void testRatingOutOfBound() {
        l1.setRating(12);
        assertEquals(l1.getRating(), 100);
        l2.setRating(-1);
        assertEquals(l2.getRating(), 100);
    }
    @Test
    void testRatingJustInbounds(){
        l1.setRating(0);
        assertEquals(l1.getRating(), 0);
        l2.setRating(10);
        assertEquals(l2.getRating(), 10);
    }

    @Test
    void testComments(){
        l1.getComments().add("Dogs need leashes");
        assertEquals(l1.getComments().get(0), "Dogs need leashes");
    }

    @Test
    void testToJsonLocation() {
        assertEquals("UBC", l1.toJson().get("name"));
        assertEquals("4268 ABC Street", l1.toJson().get("streetName"));
        assertEquals("V7N3H9", l1.toJson().get("postalCode"));
        assertEquals("Vancouver", l1.toJson().get("city"));
        assertEquals("BC", l1.toJson().get("province"));
        assertEquals(20, l1.toJson().get("rating"));
        assertEquals("[]", l1.toJson().get("comments").toString());
    }


//    @Test
//    void testCommentsToJson() {
//        JSONArray comments1 = new JSONArray();
//        ArrayList<String> comments = new ArrayList<>();
//        comments.add("hello");
//        comments.add("hi");
//        comments.add("abc");
//
//    }
    //   l1 = new Location("UBC", "4268 ABC Street", "V7N3H9", "Vancouver", "BC");
//    @Test
//    void testGetComments(){
//        assertEquals("", l1.getComments());
//        l1.setComments(Dog needs leash);
//        assertEquals("Dog needs leash", l1.getComments());
//    }
}
