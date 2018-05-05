package factions;


import gameRunner.Player;
import gameRunner.Combo;

public class StarksFaction extends BaseFaction {
	public StarksFaction(Player p){
		super(p);
		message = "If you roll Jon Snow, you will recieve an extra roll!\n" + 
			      "You can recieve a maximum of one extra roll per round.";
		condition = () -> Boolean.valueOf(Combo.numSpecialsByType(p.getDie(), 1) >= 1);
		exec = () -> p.setMaxRolls(4);
	}

	public void resetFaction(){
		super.resetFaction();
		p.setMaxRolls(3);

	}

	public Faction getFactionType(){
		return Faction.STARKS;
	}
}
