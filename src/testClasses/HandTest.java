package testClasses;

import gameRunner.Die;
import gameRunner.Hand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void incrementMaxRolls() {
        Hand hand = new Hand(5, 5, 3);
        hand.incrementMaxRolls();
    }

    @Test
    void getDieSides() {
        Hand hand = new Hand(5, 5, 3);
        assertEquals(5, hand.getDieSides(), "getDieSides returned the wrong amount of dieSides");
    }

    @Test
    void getRolls() {
        Hand hand = new Hand(5, 5, 3);
        hand.getRolls();
    }

    @Test
    void getDieByIndex() {
        Hand hand = new Hand(5, 5, 3);
        Die die = new Die(1, false);
        hand.setDieByIndex(0, die);
        assertEquals(0, hand.getDieByIndex(0).compareTo(die), "setDieByIndex or getDieByIndex has an error");
    }

    @Test
    void setDieByIndex() {
        Hand hand = new Hand(5, 5, 3);
        Die die = new Die(1, false);
        hand.setDieByIndex(0, die);
        assertEquals(0, hand.getDieByIndex(0).compareTo(die), "setDieByIndex or getDieByIndex has an error");
    }

    @Test
    void shuffle() {
        Hand hand = new Hand(5,5,3);
        boolean[] keep = new boolean[5];
        for (int i = 0; i < keep.length; i++) {
            keep[i] = true;
        }
        assertEquals(true, hand.shuffle(keep), "There was an error in hand shuffle");
    }

    @Test
    void shuffleAll() {
        Hand hand = new Hand(5,5,3);
        hand.shuffleAll();
    }

    @Test
    void setRolls() {
        //TODO: This function was incomplete at test creation
        Hand hand = new Hand(5,5,3);
        Die[] rolls = new Die[3];
        for (int i = 0; i < 3; i++) rolls[i] = new Die();
//        hand.setRolls(rolls);
    }

    @Test
    void cloneTest() {
        Hand hand = new Hand(5,5,3);
        Hand newHand = hand.clone();
        assertEquals(hand.getRollNum(), newHand.getRollNum(), "Clone did not properly copy rolls");
        assertEquals(hand.getDieSides(), newHand.getDieSides(), "Clone did not properly copy die sides");
    }

    @Test
    void getRollNum() {
        Hand hand = new Hand(5,5,3);
        assertEquals(0, hand.getRollNum(), "The amount of rolls in get roll num is wrong");
    }
}