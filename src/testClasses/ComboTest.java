package testClasses;

import gameRunner.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComboTest {

    @Test
    void maxOfAKindFound() {
        Hand h = new Hand(7,7,3);
        h.setDieByIndex(0,new Die(4,true));
        h.setDieByIndex(1,new Die(4,true));
        h.setDieByIndex(2,new Die(2,true));
        h.setDieByIndex(3,new Die(3,true));
        assertEquals(3, Combo.maxOfAKindFound(h.getRolls()));
    }

    @Test
    void numSpecialsByType() {
        Hand h = new Hand(7,7,3);
        h.setDieByIndex(0,new Die(4,true));
        h.setDieByIndex(1,new Die(4,true));
        h.setDieByIndex(2,new Die(2,true));
        h.setDieByIndex(3,new Die(3,true));
        assertEquals(2, Combo.numSpecialsByType(h.getRolls(), 4));
    }

    @Test
    void northFound() {
        Hand h = new Hand(7,7,3);
        h.setDieByIndex(0,new Die(1,true));
        h.setDieByIndex(1,new Die(1,true));
        h.setDieByIndex(2,new Die(1,true));
        h.setDieByIndex(3,new Die(1,true));
        h.setDieByIndex(4,new Die(1,true));
        h.setDieByIndex(5,new Die(1,true));
        h.setDieByIndex(6,new Die(1,true));

        assertEquals(true, Combo.northFound(h.getRolls()));
        h.setDieByIndex(0,new Die(3,true));
        h.setDieByIndex(1,new Die(3,true));
        h.setDieByIndex(2,new Die(3,true));
        h.setDieByIndex(3,new Die(1,true));
        h.setDieByIndex(4,new Die(1,true));
        h.setDieByIndex(5,new Die(1,true));
        h.setDieByIndex(6,new Die(1,true));
        assertEquals(false, Combo.northFound(h.getRolls()));
    }

    @Test
    void southFound() {
            Hand h = new Hand(7,7,3);
            h.setDieByIndex(0,new Die(2,true));
            h.setDieByIndex(1,new Die(2,true));
            h.setDieByIndex(2,new Die(2,true));
            h.setDieByIndex(3,new Die(2,true));
            h.setDieByIndex(4,new Die(2,true));
            h.setDieByIndex(5,new Die(1,true));
            h.setDieByIndex(6,new Die(1,true));

            assertEquals(true, Combo.southFound(h.getRolls()));

            h.setDieByIndex(4,new Die(1,true));

            assertEquals(false, Combo.southFound(h.getRolls()));
    }

    @Test
    void easterosFound() {
        Hand h = new Hand(7,7,3);
        h.setDieByIndex(0,new Die(3,true));
        h.setDieByIndex(1,new Die(3,true));
        h.setDieByIndex(2,new Die(3,true));
        h.setDieByIndex(3,new Die(3,true));
        h.setDieByIndex(4,new Die(3,true));
        h.setDieByIndex(5,new Die(1,true));
        h.setDieByIndex(6,new Die(1,true));

        assertEquals(true, Combo.easterosFound(h.getRolls()));

        h.setDieByIndex(4,new Die(1,true));

        assertEquals(false, Combo.easterosFound(h.getRolls()));
    }

    @Test
    void deadFound() {
        Hand h = new Hand(7,7,3);
        h.setDieByIndex(0,new Die(4,true));
        h.setDieByIndex(1,new Die(4,true));
        h.setDieByIndex(2,new Die(4,true));
        h.setDieByIndex(3,new Die(4,true));
        h.setDieByIndex(4,new Die(4,true));
        h.setDieByIndex(5,new Die(1,true));
        h.setDieByIndex(6,new Die(1,true));

        assertEquals(true, Combo.deadFound(h.getRolls()));

        h.setDieByIndex(4,new Die(1,true));

        assertEquals(false, Combo.deadFound(h.getRolls()));
    }

    @Test
    void crownFound() {
        Hand h = new Hand(7,7,3);
        h.setDieByIndex(0,new Die(5,true));
        h.setDieByIndex(1,new Die(5,true));
        h.setDieByIndex(2,new Die(5,true));
        h.setDieByIndex(3,new Die(5,true));
        h.setDieByIndex(4,new Die(5,true));
        h.setDieByIndex(5,new Die(1,true));
        h.setDieByIndex(6,new Die(1,true));

        assertEquals(true, Combo.crownFound(h.getRolls()));

        h.setDieByIndex(4,new Die(1,true));

        assertEquals(false, Combo.crownFound(h.getRolls()));
    }

    @Test
    void othersFound() {
        Hand h = new Hand(7,7,3);
        h.setDieByIndex(0,new Die(6,true));
        h.setDieByIndex(1,new Die(6,true));
        h.setDieByIndex(2,new Die(6,true));
        h.setDieByIndex(3,new Die(6,true));
        h.setDieByIndex(4,new Die(6,true));
        h.setDieByIndex(5,new Die(1,true));
        h.setDieByIndex(6,new Die(1,true));

        assertEquals(true, Combo.othersFound(h.getRolls()));

        h.setDieByIndex(4,new Die(1,true));

        assertEquals(false, Combo.othersFound(h.getRolls()));
    }

    @Test
    void dragonsFound() {
        Hand h = new Hand(7,7,3);
        h.setDieByIndex(0,new Die(7,true));
        h.setDieByIndex(1,new Die(7,true));
        h.setDieByIndex(2,new Die(7,true));
        h.setDieByIndex(3,new Die(7,true));
        h.setDieByIndex(4,new Die(7,true));
        h.setDieByIndex(5,new Die(1,true));
        h.setDieByIndex(6,new Die(1,true));

        assertEquals(true, Combo.dragonsFound(h.getRolls()));

        h.setDieByIndex(4,new Die(1,true));

        assertEquals(false, Combo.dragonsFound(h.getRolls()));
    }

    @Test
    void maxOfGivenKindFound() {
        Hand h = new Hand(7,7,3);
        h.setDieByIndex(0,new Die(7,true));
        h.setDieByIndex(1,new Die(7,true));
        h.setDieByIndex(2,new Die(7,true));
        h.setDieByIndex(3,new Die(4,true));
        h.setDieByIndex(4,new Die(7,true));
        h.setDieByIndex(5,new Die(1,true));
        h.setDieByIndex(6,new Die(1,true));

        assertEquals(4, Combo.maxOfGivenKindFound(7 , h.getRolls()));
    }

    @Test
    void fullHouseFound() {
        Hand h = new Hand(7,7,3);
        h.setDieByIndex(0,new Die(7,true));
        h.setDieByIndex(1,new Die(7,true));
        h.setDieByIndex(2,new Die(7,true));
        h.setDieByIndex(3,new Die(7,true));
        h.setDieByIndex(4,new Die(4,true));
        h.setDieByIndex(5,new Die(4,true));
        h.setDieByIndex(6,new Die(4,true));

        assertEquals(true, Combo.fullHouseFound(h.getRolls()));

        h.setDieByIndex(4,new Die(7,true));
        h.setDieByIndex(5,new Die(7,true));

        assertEquals(true, Combo.fullHouseFound(h.getRolls()));

        h.setDieByIndex(5,new Die(6,true));

        assertEquals(false, Combo.fullHouseFound(h.getRolls()));
    }
}