package factions;

import gameRunner.Combo;
import gameRunner.Player;

public class TargaryenFaction extends BaseFaction {

	public TargaryenFaction(Player p) {
		super(p);
		message = "If you roll Drogon, all of your current multipliers double!";
		condition = () -> Boolean.valueOf(Combo.numSpecialsByType(p.getDie(), 7) >= 1);
		exec = () -> {
			for(int i=1; i<=p.getHand().getDieSides(); i++){
				int newVal= p.getScorer().getMultiplierByType(i) * 2;
				p.getScorer().setMultiplierByType(i, newVal);
			}
		};
	}

	public Faction getFactionType(){
		return Faction.TARGARYEN;
	}
}
