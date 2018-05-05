package testClasses;

import factions.Faction;
import factions.LannistersFaction;
import gameRunner.Die;
import gameRunner.Player;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LannistersFactionTest {
    @Test
    void getFactionType() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        LannistersFaction lannistersFaction = new LannistersFaction(player);
        assertEquals(Faction.LANNISTERS, lannistersFaction.getFactionType(),
                "Doesn't return correct Faction.");
    }

    @Test
    void isSpecialHand() {
        Die[] special = new Die[7];
        special[0] = new Die(2, true);
        special[1] = new Die(5, true);
        for (int i = 2; i < 7; i++) {
            special[i] = new Die(2, true);
        }

        Player player = new Player(7, 7, 3, "Nicole", Faction.LANNISTERS);
        LannistersFaction lannistersFaction = new LannistersFaction(player);
        player.getHand().setRolls(special);
        assertEquals(true, lannistersFaction.isSpecialHand(), "returns incorrect special hand.");
    }

    @Test
    void executeSpecial() {
        Die[] special = new Die[7];
        special[0] = new Die(2, true);
        special[1] = new Die(5, true);
        for (int i = 2; i < 7; i++) {
            special[i] = new Die(2, false);
        }

        Player player = new Player(7, 7, 3, "Nicole", Faction.LANNISTERS);
        LannistersFaction lannistersFaction = new LannistersFaction(player);
        player.getHand().setRolls(special);

        lannistersFaction.executeSpecial();
        int count = 0;
        for(Die d : Arrays.asList(player.getDie())) {
            if(!d.isSpecial() && d.getType() == 5){
                count++;
            }
        }
        assertEquals(5, count, "Does not change the dice correctly.");
    }

    @Test
    void resetFaction() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.LANNISTERS);
        LannistersFaction lannistersFaction = new LannistersFaction(player);
        lannistersFaction.resetFaction();
        assertEquals(false, lannistersFaction.isExecuted(), "Did not reset faction.");
    }

    @Test
    void specialInstructions() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.LANNISTERS);
        LannistersFaction lannistersFaction = new LannistersFaction(player);
        assertEquals("If you roll Jaime Lannister AND Cersei Lannister, change all\n" +
                "non-rare rolls to Wildfire.", lannistersFaction.specialInstructions(), "instructions" +
                "dont match");
    }

    @Test
    void isExecuted() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.LANNISTERS);
        LannistersFaction lannistersFaction = new LannistersFaction(player);
        assertEquals(false, lannistersFaction.isExecuted(), "Shouldn't have executed.");
    }
}