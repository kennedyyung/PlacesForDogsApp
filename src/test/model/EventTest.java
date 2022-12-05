package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    private Event testEvent;
    private Date testDate;

    @BeforeEach
    public void runBefore() {
        testEvent = new Event("Location has been added");
        testDate = Calendar.getInstance().getTime();
    }

    @Test
    public void TestEvent() {
        assertEquals("Location has been added", testEvent.getDescription());
        String testDateString = testDate.toString();
        assertEquals(testDateString, testEvent.getDate().toString());
    }
}
