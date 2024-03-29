package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

//Inspired by AlarmSystem
//https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

//Is a log of the PlacesForDogsApp's events
public class EventLog implements Iterable<Event> {

    private static EventLog theLog;
    private Collection<Event> events;

    //EFFECTS: constructs the eventlog as an array of events
    private EventLog() {
        events = new ArrayList<Event>();
    }

    //MODIFIES: this
    //EFFECTS: returns instance of eventlog or creates one if it does not exist
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    //REQUIRES: Event
    //MODIFIES: this
    //EFFECTS: adds an event to the event log
    public void logEvent(Event e) {
        events.add(e);
    }

    //EFFECTS: clears eventlog and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared"));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
