package factions;

import gameRunner.Combo;
import gameRunner.Player;

public class BaratheonFaction extends BaseFaction {

	public BaratheonFaction(Player p) {
		super(p);
		message = "If you roll 3 Kingsgaurd Knights, add 10 pts to this hand's Score!";
	}

	@Override
	public boolean isSpecialHand() {
		return Combo.maxOfGivenKindFound(2, p.getDie()) >= 3;
	}
	
	public void executeSpecial(){
		super.executeSpecial();
		p.setBonusPoints(10);
	}
	
	public void resetFaction(){
		super.resetFaction();
		p.setBonusPoints(0);
	}
}
