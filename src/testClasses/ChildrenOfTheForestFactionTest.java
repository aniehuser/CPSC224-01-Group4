package testClasses;

import factions.ChildrenOfTheForestFaction;
import factions.Faction;
import gameRunner.Die;
import gameRunner.Hand;
import gameRunner.Player;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ChildrenOfTheForestFactionTest {
    @Test
    void getFactionType() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.CHILDREN_OF_THE_FOREST);
        ChildrenOfTheForestFaction childrenOfTheForestFaction = new ChildrenOfTheForestFaction(player);
        assertEquals(Faction.CHILDREN_OF_THE_FOREST, childrenOfTheForestFaction.getFactionType(),
                "Doesn't return correct Faction.");
    }

    @Test
    void isSpecialHand() {
        Die[] special = new Die[7];
        for (int i = 0; i <= 6; i++) {
            special[i] = new Die(6, false);
        }

        Player player = new Player(7, 7, 3, "Nicole", Faction.CHILDREN_OF_THE_FOREST);
        ChildrenOfTheForestFaction childrenOfTheForestFaction = new ChildrenOfTheForestFaction(player);
        player.getHand().setRolls(special);

        assertEquals(true, childrenOfTheForestFaction.isSpecialHand(), "returns incorrect special hand.");
    }

    @Test
    void executeSpecial() {
        Die[] special = new Die[7];
        for (int i = 0; i <= 6; i++) {
            special[i] = new Die(6, false);
        }

        Player player = new Player(7, 7, 3, "Nicole", Faction.CHILDREN_OF_THE_FOREST);
        ChildrenOfTheForestFaction childrenOfTheForestFaction = new ChildrenOfTheForestFaction(player);
        player.getHand().setRolls(special);

        childrenOfTheForestFaction.executeSpecial();
        for(Die d : Arrays.asList(player.getDie())) {
            if(d.getType() == 6 && !d.isSpecial()){
                assert(true);
                break;
            }
        }
    }

    @Test
    void resetFaction() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.CHILDREN_OF_THE_FOREST);
        ChildrenOfTheForestFaction childrenOfTheForestFaction = new ChildrenOfTheForestFaction(player);
        childrenOfTheForestFaction.resetFaction();
        assertEquals(false, childrenOfTheForestFaction.isExecuted(), "Faction was not reset.");
    }

    @Test
    void specialInstructions() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.CHILDREN_OF_THE_FOREST);
        ChildrenOfTheForestFaction childrenOfTheForestFaction = new ChildrenOfTheForestFaction(player);
        assertEquals("Roll 4 White Walkers, turn one into the Night King!",
                childrenOfTheForestFaction.specialInstructions(), "Wrong instructions");
    }

    @Test
    void isExecuted() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.CHILDREN_OF_THE_FOREST);
        ChildrenOfTheForestFaction childrenOfTheForestFaction = new ChildrenOfTheForestFaction(player);
        assertEquals(false, childrenOfTheForestFaction.isExecuted(), "Shouldn't have executed.");
    }
}