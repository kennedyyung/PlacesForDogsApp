package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventLogTest {
    private Event e1;
    private Event e2;
    private Event e3;

    @BeforeEach
    public void loadEvents() {
        e1 = new Event("test1");
        e2 = new Event("test2");
        e3 = new Event("test3");
        EventLog testEL = EventLog.getInstance();
        testEL.logEvent(e1);
        testEL.logEvent(e2);
        testEL.logEvent(e3);
    }

    @Test
    public void testLogEvent() {
    List<Event> testList = new ArrayList<>();

    EventLog testEL = EventLog.getInstance();
    for (Event next: testEL) {
        testList.add(next);
    }

    assertTrue(testList.contains(e1));
    assertTrue(testList.contains(e2));
    assertTrue(testList.contains(e3));
    }
}
