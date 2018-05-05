package testClasses;

import factions.Faction;
import gameRunner.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void getHand() {
        Hand hand = new Hand();
        Score score = new Score();
        score.calculateScore(hand);
        assertEquals(0, hand.compareTo(score.getHand()), "getHand did not function properly");
    }

    @Test
    void getScore() {
        Player playerTest = new Player(3,3,3,"Test", Faction.GREYJOYS);
        playerTest.setScore("");
    }

    @Test
    void generateScoreMessage() {
        Game game = new Game();
        game.start();
        Die[] dies = new Die[game.getDieNum()];
        for (int i = 0; i < dies.length; i++) {
            dies[i] = new Die(i, false);
        }
        Hand hand = new Hand();
        hand.setRolls(dies);
        Score score = new Score();
        score.calculateScore(hand);
        assertEquals( "0 on the Dragons line", score.generateScoreMessage("Dragons"));

    }

    @Test
    void calculateScore() {
        Hand hand = new Hand(3,3,3);
        Score score = new Score();
        score.calculateScore(hand);
    }

    @Test
    void sortHand() {
        Game game = new Game();
        game.start();
        Die[] dies = new Die[game.getDieNum()];
        for (int i = 0; i < dies.length; i++) {
            dies[i] = new Die(i, false);
        }
        Hand hand = new Hand(3,3,3);
        hand.setRolls(dies);
        Score score = new Score();
        score.calculateScore(hand);
        score.sortHand();
        assertEquals(0, hand.compareTo(score.getHand()), "There was an error sorting the hand");
    }

    @Test
    void totalAllDice() {
        Game game = new Game();
        game.start();
        Die[] dies = new Die[game.getDieNum()];
        for (int i = 0; i < dies.length; i++) {
            dies[i] = new Die(i, false);
        }
        Hand hand = new Hand();
        hand.setRolls(dies);
        int sum = 0;
        for(int i=0; i<dies.length; i++){
            sum += dies[i].getValue();
        }
        Score score = new Score();
        score.calculateScore(hand);
        assertEquals(sum, score.totalAllDice());
    }

    @Test
    void calculateUpperScore() {
        Game game = new Game();
        game.start();
        Die[] dies = new Die[game.getDieNum()];
        for (int i = 0; i < dies.length; i++) {
            dies[i] = new Die(i, false);
        }
        Hand hand = new Hand(3,game.getDieNum(),3);
        hand.setRolls(dies);
        Score score = new Score();
        score.calculateScore(hand);
        score.calculateUpperScore();

    }

    @Test
    void getMultiplierByType() {
        Score score = new Score();
        score.setMultiplierByType(1,3);
        assertEquals(3, score.getMultiplierByType(1), "get or set multiplier by type is wrong");
    }

    @Test
    void setMultiplierByType() {
        Score score = new Score();
        assertEquals(0, score.getMultiplierByType(1), "get or set multiplier by type is wrong");
    }

    @Test
    void calculateLowerScore() {
        Game game = new Game();
        game.start();
        Die[] dies = new Die[game.getDieNum()];
        for (int i = 0; i < dies.length; i++) {
            dies[i] = new Die(i, false);
        }
        Hand hand = new Hand(3,game.getDieNum(),3);
        hand.setRolls(dies);
        Score score = new Score();
        score.calculateScore(hand);
        score.calculateLowerScore();
    }
}