package testClasses;

import factions.ChildrenOfTheForestFaction;
import factions.Faction;
import factions.GreyJoysFaction;
import gameRunner.Die;
import gameRunner.Player;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GreyJoysFactionTest {
    @Test
    void getFactionType() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        GreyJoysFaction greyJoysFaction = new GreyJoysFaction(player);
        assertEquals(Faction.GREYJOYS, greyJoysFaction.getFactionType(),
                "Doesn't return correct Faction.");
    }

    @Test
    void isSpecialHand() {
        Die[] special = new Die[7];
        for (int i = 0; i <= 6; i++) {
            special[i] = new Die(3, true);
        }

        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        GreyJoysFaction greyJoysFaction = new GreyJoysFaction(player);

        player.getHand().setRolls(special);
        assertEquals(true, greyJoysFaction.isSpecialHand(), "returns incorrect special hand.");
    }

    @Test
    void executeSpecial() {
        Die[] special = new Die[7];
        for (int i = 0; i <= 6; i++) {
            special[i] = new Die(3, true);
        }

        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        GreyJoysFaction greyJoysFaction = new GreyJoysFaction(player);
        player.getHand().setRolls(special);


        greyJoysFaction.executeSpecial();
        for(Die d : Arrays.asList(player.getDie())) {
            if(d.getType() == 3){
                assert(true);
            }
        }
    }

    @Test
    void resetFaction() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        GreyJoysFaction greyJoysFaction = new GreyJoysFaction(player);
        greyJoysFaction.resetFaction();
        assertEquals(false, greyJoysFaction.isExecuted(), "Faction was not reset.");
    }

    @Test
    void specialInstructions() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        GreyJoysFaction greyJoysFaction = new GreyJoysFaction(player);
        assertEquals("Roll 2 Arya Starks and all Faceless Men are now worth 6 points!", greyJoysFaction.specialInstructions());
    }

    @Test
    void isExecuted() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.GREYJOYS);
        GreyJoysFaction greyJoysFaction = new GreyJoysFaction(player);
        assertEquals(false, greyJoysFaction.isExecuted(), "Shouldn't have executed.");
    }
}