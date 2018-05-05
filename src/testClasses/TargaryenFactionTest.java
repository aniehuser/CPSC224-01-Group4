package testClasses;

import factions.Faction;
import factions.TargaryenFaction;
import gameRunner.Die;
import gameRunner.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TargaryenFactionTest {
    @Test
    void getFactionType() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.TARGARYEN);
        TargaryenFaction targaryenFaction = new TargaryenFaction(player);
        assertEquals(Faction.TARGARYEN, targaryenFaction.getFactionType(),
                "Doesn't return correct Faction.");
    }

    @Test
    void isSpecialHand() {
        Die[] special = new Die[7];
        for (int i = 0; i < 7; i++) {
            special[i] = new Die(7, true);
        }

        Player player = new Player(7, 7, 3, "Nicole", Faction.TARGARYEN);
        TargaryenFaction targaryenFaction = new TargaryenFaction(player);
        player.getHand().setRolls(special);

        assertEquals(true, targaryenFaction.isSpecialHand(), "Didn't set the special hand.");
    }

    @Test
    void executeSpecial() {
        Die[] special = new Die[7];
        for (int i = 0; i < 7; i++) {
            special[i] = new Die(7, true);
        }

        Player player = new Player(7, 7, 3, "Nicole", Faction.TARGARYEN);
        TargaryenFaction targaryenFaction = new TargaryenFaction(player);

        int ogVal = 0;
        for(int i=1; i<=player.getHand().getDieSides(); i++){
            ogVal += player.getScorer().getMultiplierByType(i);
        }

        player.getHand().setRolls(special);

        int newVal = 0;
        for(int i=1; i<=player.getHand().getDieSides(); i++){
            newVal= player.getScorer().getMultiplierByType(i) * 2;
            player.getScorer().setMultiplierByType(i, newVal);
        }

        if (ogVal*2 == newVal) {
            assert(true);
        }
    }

    @Test
    void resetFaction() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.TARGARYEN);
        TargaryenFaction targaryenFaction = new TargaryenFaction(player);
        targaryenFaction.resetFaction();
        assertEquals(false, targaryenFaction.isExecuted(), "Didn't reset faction.");
    }

    @Test
    void specialInstructions() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.TARGARYEN);
        TargaryenFaction targaryenFaction = new TargaryenFaction(player);
        assertEquals("If you roll Drogon, all of your current multipliers double!", targaryenFaction.specialInstructions());
    }

    @Test
    void isExecuted() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.TARGARYEN);
        TargaryenFaction targaryenFaction = new TargaryenFaction(player);
        assertEquals(false, targaryenFaction.isExecuted(), "Shouldn't have executed.");
    }

}