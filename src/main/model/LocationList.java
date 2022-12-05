package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//Represents a location list
public class LocationList implements Writable {
    private String locationListName;
    private ArrayList<Location> locationList;


    //MODIFIES: this
    //EFFECTS: creates new array list and sets location list name
    public LocationList(String locationListName) {
        this.locationListName = locationListName;
        locationList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: creates new ArrayList with untitled name
    public LocationList() {
        this.locationListName = "Untitled";
        locationList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds location to LocationList
    public void addLocation(Location l) {

        EventLog.getInstance().logEvent(new Event(l.getName()
                + " has been added to " + getLocationListName() + "\n"));
        locationList.add(l);
    }

    //MODIFIES: this
    //EFFECTS: removes location from LocationList
    public void removeLocation(Location l) {
        locationList.remove(l);
    }

    public String getLocationListName() {
        return locationListName;
    }


    public void setLocationListName(String locationListName) {
        this.locationListName = locationListName;
        EventLog.getInstance().logEvent(new Event("A Location list named "
                + locationListName + " has been created" + "\n"));
    }

    public ArrayList<Location> getLocationList() {
        return this.locationList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("locationListName", locationListName);
        json.put("list", listToJson());
        return json;
    }

    //MODIFIES: this
    //EFFECTS: Turns locationlist into JSONarray
    private JSONArray listToJson() {
        JSONArray listArray = new JSONArray();
        for (Location l : locationList) {
            listArray.put(l.toJson());

        }
        return listArray;
    }


}
