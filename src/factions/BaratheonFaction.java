package factions;

import gameRunner.Combo;
import gameRunner.Player;

public class BaratheonFaction extends BaseFaction {

	public BaratheonFaction(Player p) {
		super(p);
		message = "If you roll 3 Kingsgaurd Knights, add 10 pts to this hand's Score!";
		condition = () -> Boolean.valueOf(Combo.maxOfGivenKindFound(2, p.getDie()) >= 3);
		exec = () -> p.setBonusPoints(10);
	}
	
	public void resetFaction(){
		super.resetFaction();
		p.setBonusPoints(0);
	}
}
