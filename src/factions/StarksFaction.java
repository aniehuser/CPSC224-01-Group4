package factions;


import gameRunner.Player;
import gameRunner.Combo;

public class StarksFaction extends BaseFaction {
	
	public StarksFaction(Player p){
		super(p);
		message = "If you roll Jon Snow, you will recieve an extra roll!\n" + 
			      "You can recieve a maximum of one extra roll per round.";
	}
	
	public boolean isSpecialHand(){
		return Combo.numSpecialsByType(p.getDie(), 1) >= 1;
	}
	
	public void executeSpecial(){
		super.executeSpecial();
		p.incrementMaxRolls();
	}

	public Faction getFactionType(){
		return Faction.BARATHEON;
	}
}
