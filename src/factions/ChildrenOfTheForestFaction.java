package factions;

import java.util.Arrays;

import gameRunner.Combo;
import gameRunner.Player;
import gameRunner.Die;

public class ChildrenOfTheForestFaction extends BaseFaction {

	public ChildrenOfTheForestFaction(Player p) {
		super(p);
		message = "Roll 4 White Walkers, turn one into the Night King!";
		condition = () -> Boolean.valueOf(Combo.maxOfGivenKindFound(6, p.getDie()) >= 4);
		exec = () -> {
			for(Die d : Arrays.asList(p.getDie())){
				if(d.getType() == 6 && !d.isSpecial()){
					d.setSpecial(true);
					break;
				}
			}
		};
	}
}
