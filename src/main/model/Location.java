package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//Represents a location with a name, the streetname, postalcode, city, province, ratings, and comments
public class Location implements Writable {
    private String name;
    private String streetName;
    private String postalCode;
    private String city;
    private String province;
    private int rating;
    private ArrayList<String> comments;

    //EFFECTS: name is set to this.name, streetName is set to this.streetName, postalCode is set to this.postalCode,
    // city is set to this.city, province is set to this.province, rating is set to this.rating,
    // comments are set to this.comments

    public Location(String name, String streetName, String postalCode, String city, String province,
                    ArrayList<String> comments, int rating) {
        this.name = name;
        this.streetName = streetName;
        this.postalCode = postalCode;
        this.city = city;
        this.province = province;
        this.comments = comments;
        if (!comments.isEmpty()) {
            for (String c : this.comments) {
                EventLog.getInstance().logEvent(new Event("The comment " + c
                        + "has been added " + "\n"));
            }
        }
        this.rating = rating;

    }

    //EFFECTS: name is set to this.name, streetName is set to this.streetName, postalCode is set to this.postalCode,
    // city is set to this.city, province is set to this.province, rating is set at 20 to begin, and comments are a
    // new arraylist
    public Location(String name, String streetName, String postalCode, String city, String province) {
        this.name = name;
        EventLog.getInstance().logEvent(new Event("Location name has been set to "
                + this.name + "\n"));
        this.streetName = streetName;
        EventLog.getInstance().logEvent(new Event("Street name has been set to "
                + this.streetName + "\n"));
        this.postalCode = postalCode;
        EventLog.getInstance().logEvent(new Event("Postal Code has been set to "
                + this.postalCode + "\n"));
        this.city = city;
        EventLog.getInstance().logEvent(new Event("City has been set to "
                + this.city + "\n"));
        this.province = province;
        EventLog.getInstance().logEvent(new Event("Province has been set to "
                + this.province + "\n"));
        this.rating = 20;
        this.comments = new ArrayList<>();
        if (!this.comments.isEmpty()) {
            for (String c : this.comments) {
                EventLog.getInstance().logEvent(new Event("The comment " + c
                        + "has been added " + "\n"));
            }
        }
//        if (comments.size() >= 1) {
//            EventLog.getInstance().logEvent(new Event("The comment " + this.comments.get(this.comments.size() - 1)
//                    + "has been added " + "\n"));
//        }
    }


    //EFFECTS: puts together the different parts to form a full address
    public String fullAddress() {
        String address = this.streetName + ", " + this.postalCode + ", " + this.city + ", " + this.province;
        return address;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getRating() {
        return rating;
    }

    //REQUIRES: a rating >= 0 && rating <= 10
    //MODIFIES: this
    public void setRating(int rating) {
        if (rating >= 0 && rating <= 10) {
            this.rating = rating;
        } else {
            this.rating = 100;
        }
        EventLog.getInstance().logEvent(new Event("Rating has been set to "
                + this.rating + "\n"));
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("streetName", streetName);
        json.put("postalCode", postalCode);
        json.put("city", city);
        json.put("province", province);
        json.put("rating", rating);
        json.put("comments", commentsToJson());
        return json;
    }

    //MODIFIES: this
    //EFFECTS: turns the comments into a JSONArray
    private JSONArray commentsToJson() {
        JSONArray commentsArray = new JSONArray();
        for (String c : comments) {
            commentsArray.put(c);
        }

        return commentsArray;
    }






}
