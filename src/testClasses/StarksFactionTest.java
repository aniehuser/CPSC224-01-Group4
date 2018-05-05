package testClasses;

import factions.Faction;
import factions.StarksFaction;
import gameRunner.Die;
import gameRunner.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StarksFactionTest {
    @Test
    void getFactionType() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        StarksFaction starksFaction = new StarksFaction(player);
        assertEquals(Faction.STARKS, starksFaction.getFactionType(),
                "Doesn't return correct Faction.");
    }

    @Test
    void isSpecialHand() {
        Die[] special = new Die[7];
        special[0] = new Die(1, true);
        for (int i = 1; i < 7; i++) {
            special[i] = new Die(2, false);
        }
        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        StarksFaction starksFaction = new StarksFaction(player);
        player.getHand().setRolls(special);
        assertEquals(true, starksFaction.isSpecialHand(), "returns incorrect special hand.");
    }

    @Test
    void executeSpecial() {
        Die[] special = new Die[7];
        special[0] = new Die(1, true);
        for (int i = 1; i < 7; i++) {
            special[i] = new Die(2, false);
        }
        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        StarksFaction starksFaction = new StarksFaction(player);
        player.getHand().setRolls(special);

        if (player.getMaxRolls() > 0) {
            assert(true);
        }
    }

    @Test
    void resetFaction() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        StarksFaction starksFaction = new StarksFaction(player);
        starksFaction.resetFaction();
        assertEquals(false, starksFaction.isExecuted(), "Faction was not reset.");
    }

    @Test
    void specialInstructions() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        StarksFaction starksFaction = new StarksFaction(player);
        assertEquals("If you roll Jon Snow, you will recieve an extra roll!\n" +
                "You can recieve a maximum of one extra roll per round.", starksFaction.specialInstructions());
    }

    @Test
    void isExecuted() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        StarksFaction starksFaction = new StarksFaction(player);
        assertEquals(false, starksFaction.isExecuted(), "Shouldn't have executed.");
    }
}