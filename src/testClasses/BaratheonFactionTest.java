package testClasses;

import factions.BaratheonFaction;
import factions.Faction;
import gameRunner.Die;
import gameRunner.Hand;
import gameRunner.Player;
import org.junit.jupiter.api.Test;
import factions.BaseFaction;
import static org.junit.jupiter.api.Assertions.*;

class BaratheonFactionTest {
    @Test
    void resetFaction() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.BARATHEON);
        BaratheonFaction baratheonFaction = new BaratheonFaction(player);
        baratheonFaction.resetFaction();
        assertEquals(false, baratheonFaction.isExecuted(), "Faction was not reset.");
        assertEquals(0, player.getBonusPoints(), "Bonus points were not reset.");
    }

    @Test
    void getFactionType() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.BARATHEON);
        BaratheonFaction baratheonFaction = new BaratheonFaction(player);
        assertEquals(Faction.BARATHEON, baratheonFaction.getFactionType(), "Doesn't return correct Faction.");
    }

    @Test
    void isSpecialHand() {
        //Create a hand that would be a correct hand for special
        Die[] special = new Die[7];
        for (int i = 0; i <= 6; i++) {
            special[i] = new Die(2, false);
        }

        Player player = new Player(7, 7, 3, "Nicole", Faction.BARATHEON);
        BaratheonFaction baratheonFaction = new BaratheonFaction(player);
        player.getHand().setRolls(special);

        baratheonFaction.isSpecialHand();
        assertEquals(true, baratheonFaction.isSpecialHand(), "returns incorrect special hand.");
    }

    @Test
    void executeSpecial() {
        Die[] special = new Die[7];
        for (int i = 0; i <= 6; i++) {
            special[i] = new Die(2, false);
        }

        Player player = new Player(7, 7, 3, "Nicole", Faction.BARATHEON);
        BaratheonFaction baratheonFaction = new BaratheonFaction(player);
        player.getHand().setRolls(special);

        baratheonFaction.executeSpecial();
        assertEquals(true, baratheonFaction.isExecuted(), "It was not executed.");
        assertEquals(10, player.getBonusPoints(), "Bonus points were not added");
    }


    @Test
    void specialInstructions() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.BARATHEON);
        BaratheonFaction baratheonFaction = new BaratheonFaction(player);
        assertEquals("If you roll 3 Kingsgaurd Knights, add 10 pts to this hand's Score!",
                baratheonFaction.specialInstructions(), "Wrong instructions.");
    }

    @Test
    void isExecuted() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.BARATHEON);
        BaratheonFaction baratheonFaction = new BaratheonFaction(player);
        assertEquals(false, baratheonFaction.isExecuted(), "Special should not be executed.");

    }
}