package testClasses;

import factions.Faction;
import factions.TargaryenFaction;
import factions.WhiteWalkersFaction;
import gameRunner.Die;
import gameRunner.Player;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WhiteWalkersFactionTest {
    @Test
    void getFactionType() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.WHITE_WALKERS);
        WhiteWalkersFaction whiteWalkersFaction = new WhiteWalkersFaction(player);
        assertEquals(Faction.WHITE_WALKERS, whiteWalkersFaction.getFactionType(),
                "Doesn't return correct Faction.");
    }

    @Test
    void isSpecialHand() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.WHITE_WALKERS);
        WhiteWalkersFaction whiteWalkersFaction = new WhiteWalkersFaction(player);
        Die[] special = new Die[7];
        for (int i = 0; i < 7; i++) {
            special[i] = new Die(6, true);
        }
        player.getHand().setRolls(special);
        assertEquals(true, whiteWalkersFaction.isSpecialHand(), "Didn't set the special hand.");
    }

    @Test
    void executeSpecial() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.WHITE_WALKERS);
        WhiteWalkersFaction whiteWalkersFaction = new WhiteWalkersFaction(player);
        Die[] special = new Die[7];
        for (int i = 0; i < 7; i++) {
            special[i] = new Die(6, true);
        }
        player.getHand().setRolls(special);
        int count = 0;
        for(Die d : Arrays.asList(player.getDie())){
            if(d.getType() == 1){
                count++;
            }
        }
        whiteWalkersFaction.executeSpecial();
        int count2 = 0;
        for(Die d : Arrays.asList(player.getDie())){
            if(d.getType() == 4){
                count2++;
            }
        }
        if (count == count2) {
            assert (true);
        }
    }

    @Test
    void resetFaction() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.WHITE_WALKERS);
        WhiteWalkersFaction whiteWalkersFaction = new WhiteWalkersFaction(player);
        whiteWalkersFaction.resetFaction();
        assertEquals(false, whiteWalkersFaction.isExecuted(), "Did not reset.");
    }

    @Test
    void specialInstructions() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.WHITE_WALKERS);
        WhiteWalkersFaction whiteWalkersFaction = new WhiteWalkersFaction(player);
        assertEquals("If you roll the Night King, all Night's Watch rangers become Undead!\n" +
                "Effect can occur once per round.", whiteWalkersFaction.specialInstructions());
    }

    @Test
    void isExecuted() {
        Player player = new Player(7, 7, 3, "Nicole", Faction.WHITE_WALKERS);
        WhiteWalkersFaction whiteWalkersFaction = new WhiteWalkersFaction(player);
        assertEquals(false, whiteWalkersFaction.isExecuted(), "Shouldn't have executed.");
    }
}