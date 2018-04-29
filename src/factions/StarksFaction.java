package factions;


import gameRunner.Player;
import gameRunner.Combo;


/**
 * Extends BaseFaction and specifies conditions and executions for Starks
 * faction.
 * 
 * CPSC 224-01, Spring 2018
 * Final Project
 * 
 * @author Anthony Niehuser
 *
 * @version v1.0  4/29/2018
 */
public class StarksFaction extends BaseFaction {
	public StarksFaction(Player p){
		super(p);
		message = "If you roll Jon Snow, you will recieve an extra roll!\n" + 
			      "You can recieve a maximum of one extra roll per round.";
		condition = () -> Boolean.valueOf(Combo.numSpecialsByType(p.getDie(), 1) >= 1);
		exec = () -> p.incrementMaxRolls();
	}

	public Faction getFactionType(){
		return Faction.STARKS;
	}
}
