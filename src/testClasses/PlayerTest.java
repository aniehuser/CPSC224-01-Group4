package testClasses;

import gameRunner.*;
import factions.Faction;
import org.junit.jupiter.api.Test;

import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void initFaction() {
        Player p1 = new Player(7,7,3,"",Faction.STARKS);
        assertEquals(Faction.STARKS, p1.getFaction().getFactionType());

        Player p2 = new Player(7,7,3,"",Faction.BARATHEON);
        assertEquals(Faction.BARATHEON, p2.getFaction().getFactionType());

        Player p3 = new Player(7,7,3,"",Faction.CHILDREN_OF_THE_FOREST);
        assertEquals(Faction.CHILDREN_OF_THE_FOREST, p3.getFaction().getFactionType());

        Player p4 = new Player(7,7,3,"",Faction.GREYJOYS);
        assertEquals(Faction.GREYJOYS, p4.getFaction().getFactionType());

        Player p5 = new Player(7,7,3,"",Faction.LANNISTERS);
        assertEquals(Faction.LANNISTERS, p5.getFaction().getFactionType());

        Player p6 = new Player(7,7,3,"",Faction.TARGARYEN);
        assertEquals(Faction.TARGARYEN, p6.getFaction().getFactionType());

        Player p7 = new Player(7,7,3,"",Faction.WHITE_WALKERS);
        assertEquals(Faction.WHITE_WALKERS, p7.getFaction().getFactionType());
    }

    @Test
    void setMaxRolls() {
        Player p = new Player(7,7,3,"", Faction.STARKS);
        p.setMaxRolls(4);
        assertEquals(4, p.getMaxRolls());
    }

    @Test
    void getFaction() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        assertEquals(Faction.BARATHEON, p.getFaction().getFactionType());
    }

    @Test
    void rollInit() {
        Player p = new Player(7,7,3,"", Faction.STARKS);
        p.rollInit();
        assertEquals(1, p.getRolls());
    }

    @Test
    void rollOnce() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        boolean[] keep = {false,false,false,false,false,false,false};
        p.rollInit();
        p.rollOnce(keep);
        assertEquals(2, p.getRolls());
        p.rollOnce(keep);
        assertEquals(3, p.getRolls());
        assertEquals(1,p.getRounds());
        p.rollOnce(keep);
        assertEquals(3, p.getRolls());

        boolean[] keepAll = {true,true,true,true,true,true,true};
        p.rollInit();
        p.rollOnce(keepAll);
        assertEquals(3, p.getRolls());
    }

    @Test
    void getScorer() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        Score s = new Score();
        assertEquals(s.getClass().getName(), p.getScorer().getClass().getName());
    }

    @Test
    void getPlayerScoreByKey() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        p.rollInit();
        p.getHand().setDieByIndex(0, new Die());
        p.getHand().setDieByIndex(1, new Die());
        p.getHand().setDieByIndex(2, new Die());
        p.getHand().setDieByIndex(3, new Die());
        p.getHand().setDieByIndex(4, new Die());
        p.getHand().setDieByIndex(5, new Die());
        p.getHand().setDieByIndex(6, new Die());

        boolean[] keep = {true,true,true,true,true,true,true};
        p.rollOnce(keep);
        p.setScore("1");
        assertEquals(7, p.getPlayerScoreByKey("1"));
    }

    @Test
    void getScoreCard() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        Hashtable<String,Integer> h = new Hashtable<String,Integer>();
        assertEquals(h.getClass().getName(), p.getScoreCard().getClass().getName());

    }

    @Test
    void getHand() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        Hand h = new Hand();
        assertEquals(h.getClass().getName(), p.getHand().getClass().getName());
    }

    @Test
    void getDie() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        Die[] d = {};
        assertEquals(d.getClass().getName(), p.getDie().getClass().getName());
    }

    @Test
    void getRolls() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        assertEquals(0, p.getRolls());
    }

    @Test
    void getMaxRolls() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        assertEquals(3, p.getMaxRolls());
    }

    @Test
    void setScore() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        p.rollInit();
        p.getHand().setDieByIndex(0, new Die());
        p.getHand().setDieByIndex(1, new Die());
        p.getHand().setDieByIndex(2, new Die());
        p.getHand().setDieByIndex(3, new Die());
        p.getHand().setDieByIndex(4, new Die());
        p.getHand().setDieByIndex(5, new Die());
        p.getHand().setDieByIndex(6, new Die());

        boolean[] keep = {true,true,true,true,true,true,true};
        p.rollOnce(keep);
        p.setScore("1");
        assertEquals(p.getScorer().getScore("1"), p.getPlayerScoreByKey("1"));
        assertEquals(true, p.isScoreSet("1"));
    }

    @Test
    void isScoreSet() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        boolean[] keep = {true,true,true,true,true,true,true};
        p.rollOnce(keep);
        p.setScore("1");
        assertEquals(true, p.isScoreSet("1"));
        assertEquals(false, p.isScoreSet("2"));
    }

    @Test
    void setBonusPoints() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        p.setBonusPoints(10);
        boolean[] keep = {true,true,true,true,true,true,true};
        p.rollInit();
        p.rollOnce(keep);
        p.setScore("1");
        assertEquals(p.getScorer().getScore("1") + 10, p.getPlayerScoreByKey("1"));
    }

    @Test
    void isRoundOver() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        assertEquals(false, p.isRoundOver());
        p.setMaxRolls(0);
        assertEquals(true, p.isRoundOver());
    }

    @Test
    void isPlayerTurnsOver() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        assertEquals(false, p.isPlayerTurnsOver());
        p.setMaxRounds(0);
        assertEquals(true, p.isPlayerTurnsOver());

    }

    @Test
    void getName() {
        Player p = new Player(7,7,3,"Bob", Faction.BARATHEON);
        assertEquals("Bob", p.getName());
    }

    @Test
    void setName() {
        Player p = new Player(7,7,3,"", Faction.BARATHEON);
        p.setName("Bob");
        assertEquals("Bob", p.getName());
    }
}