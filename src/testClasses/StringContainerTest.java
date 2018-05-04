package testClasses;

import gameRunner.StringContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringContainerTest {
    @Test
    void getString() {
        StringContainer container = new StringContainer("Hi");
        assertEquals("Hi", container.getString(), "This does not match");
    }

    @Test
    void setString() {
        StringContainer container = new StringContainer("");
        container.setString("Hi");
        assertEquals("Hi", container.getString(), "Didn't set string.");
    }

}