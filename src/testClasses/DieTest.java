package testClasses;

import gameRunner.Die;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {
    @Test
    void getValue() {
        Die die = new Die();
        die.setValue(1);
        assertEquals(1, die.getValue(), "The value getter returned a different value to the setter");
    }

    @Test
    void setValue() {
        Die die = new Die();
        die.setValue(3);
        assertEquals(3, die.getValue(), "The value getter returned a different value to the setter");
    }

    @Test
    void setType() {
        Die die = new Die();
        die.setType(1, false);
        assertEquals(1,die.getType(), "The type setter set a different value to the getter");
        assertEquals(false, die.isSpecial(), "the isSpecial method returned something different to the setter");
    }

    @Test
    void setSpecial() {
        Die die = new Die();
        die.setSpecial(true);
        assertEquals(true, die.isSpecial(), "The special setter set a different value to isSpecial()");
    }

    @Test
    void getType() {
        Die die = new Die();
        die.setType(1, false);
        assertEquals(1, die.getType(), "The setter set something different to the getter for type");
    }

    @Test
    void isSpecial() {
        Die die = new Die();
        die.setSpecial(true);
        assertEquals(true, die.isSpecial(), "The special setter set a different value to isSpecial()");
    }

    @Test
    void greaterThan() {
        Die die = new Die();
        Die die2 = new Die();
        die.setType(4, false);
        die2.setType(3, false);
        assertEquals(true, die.greaterThan(die2), "The die greater than returned an untrue comparison");

        die.setSpecial(true);
        die2.setType(4, false);
        assertEquals(true, die.greaterThan(die2), "The die greater than returned an untrue comparison");
    }
    @Test
    void lessThan() {
        Die die = new Die();
        Die die2 = new Die();
        die.setType(4, false);
        die2.setType(3, false);
        assertEquals(false, die.lessThan(die2), "The die less than returned an untrue comparison");

        die.setSpecial(true);
        die2.setType(4, false);
        assertEquals(false, die.lessThan(die2), "The die less than returned an untrue comparison");
    }

    @Test
    void equalTo() {
        Die die = new Die();
        Die die2 = new Die();
        die.setType(4, false);
        die2.setType(4, false);
        assertEquals(true, die.equalTo(die2), "The die equalTo returned an untrue comparison");
    }

    @Test
    void greaterThanEqualTo() {
        Die die = new Die();
        Die die2 = new Die();
        die.setType(4, false);
        die2.setType(4, false);
        assertEquals(true, die.greaterThanEqualTo(die2), "The die greaterThanEqualTo returned an untrue comparison");

        die.setType(5, false);
        assertEquals(true, die.greaterThanEqualTo(die2), "The die greaterThanEqualTo returned an untrue comparison");
    }

    @Test
    void lessThanEqualTo() {
        Die die = new Die();
        Die die2 = new Die();
        die.setType(4, false);
        die2.setType(4, false);
        assertEquals(true, die.lessThanEqualTo(die2), "The die lessThanEqualTo returned an untrue comparison");

        die.setType(5, false);
        assertEquals(false, die.lessThanEqualTo(die2), "The die lessThanEqualTo returned an untrue comparison");
    }

    @Test
    void notEqualTo() {
        Die die = new Die();
        Die die2 = new Die();
        die.setType(4, false);
        die2.setType(4, false);
        assertEquals(false, die.notEqualTo(die2), "The die notEqualTo returned an untrue comparison");
    }

    @Test
    void cloneTest() {
        Die die = new Die();
        Die die2 = new Die();
        die.setType(4, false);
        die2.setType(4, false);
        die.setValue(1);
        die2.setValue(1);

        assertEquals(die.getValue(), die2.getValue(), "The clone method did not properly copy die value");
        assertEquals(die.isSpecial(), die2.isSpecial(), "The clone method did not properly copy die special");
        assertEquals(die.getType(), die2.getType(), "The clone method did not properly copy die type");
    }

    @Test
    void compareTo() {
        Die die = new Die();
        Die die2 = new Die();
        die.setType(5, false);
        die2.setType(4, false);
        die.setValue(1);
        die2.setValue(1);
        assertEquals(1, die.compareTo(die2), "The compareTo method did not properly compare the Two die" );
        die.setType(4, false);
        assertEquals(0, die.compareTo(die2), "The compareTo method did not properly compare the Two die" );
        die.setType(3, false);
        assertEquals(-1, die.compareTo(die2), "The compareTo method did not properly comapre the Two die");
    }
}