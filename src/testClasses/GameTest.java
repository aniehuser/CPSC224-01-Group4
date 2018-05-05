package testClasses;

import gameRunner.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void start() {
        Game game = new Game();
        game.start();
        assertEquals(true, game.isValidInstance(), "The game start method did not work properly");
    }

    @Test
    void getDieSides() {
        Game game = new Game();
        game.start();
        assertEquals(7, game.getDieSides(), "The config file has changed or the getDieSides method is not working");
    }

    @Test
    void getDieNum() {
        Game game = new Game();
        game.start();
        assertEquals(7, game.getDieNum(), "The config file has changed or the getDieNum method is not working");
    }

    @Test
    void getRollsPerRound() {
        Game game = new Game();
        game.start();
        assertEquals(3, game.getRollsPerRound(), "The config file has changed or the getRollsPerRound method is not working");
    }

    @Test
    void getCurrentRound() {
        Game game = new Game();
        game.start();
        game.incrementRound();
        assertEquals(1, game.getCurrentRound(), "getCurrentRound or incrementRound have an error");
    }

    @Test
    void getMaxRounds() {
        Game game = new Game();
        game.start();
        assertEquals(18 ,game.getDieSides()+11, "The config file has changed or the getMaxRound function has an error");
    }

    @Test
    void incrementRound() {
        Game game = new Game();
        game.start();
        game.incrementRound();
        assertEquals(1, game.getCurrentRound(), "The increment round method had an error");
    }

    @Test
    void isValidInstance() {
        Game game = new Game();
        assertEquals(false, game.isValidInstance(), "The valid instance bool was not properly set");
    }

    @Test
    void update() {
        Game game = new Game();
        game.start();
        //asks for user input so this method cannot be automated
//        game.update();
        assertEquals(7, game.getDieNum(), "The config file has changed or the update method is not working");
    }

    @Test
    void end() {
        Game game = new Game();
        game.start();
        game.end();
    }

    @Test
    void isGameOver() {
        Game game = new Game();
        game.start();
        assertEquals(true, game.isGameOver(), "the isGameOver method is not working");
        for (int i = 0; i < game.getMaxRounds(); i++) {
            game.incrementRound();
        }

        assertEquals(false, game.isGameOver(), "the isGameOver method is not working");
    }
}