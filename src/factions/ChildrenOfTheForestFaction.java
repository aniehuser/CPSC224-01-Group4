package factions;

import java.util.Arrays;

import gameRunner.Combo;
import gameRunner.Player;
import gameRunner.Die;

public class ChildrenOfTheForestFaction extends BaseFaction {

	public ChildrenOfTheForestFaction(Player p) {
		super(p);
		message = "Roll 4 White Walkers, turn one into the Night King!";
	}

	@Override
	public boolean isSpecialHand() {
		return Combo.maxOfGivenKindFound(6, p.getDie()) >= 4;
	}
	
	public void executeSpecail(){
		super.executeSpecial();
		for(Die d : Arrays.asList(p.getDie())){
			if(d.getType() == 6 && !d.isSpecial()){
				d.setSpecial(true);
				break;
			}
		}
	}
	public Faction getFactionType(){
		return Faction.BARATHEON;
	}
}
