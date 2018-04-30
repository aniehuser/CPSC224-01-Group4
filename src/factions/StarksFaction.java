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
	/**
	 * Base constructor, sets condition, message, and exec for BaseFaction
	 * @param p reference to player associated with faction
	 */
	public StarksFaction(Player p){
		super(p);
		message = "If you roll Jon Snow, you will recieve an extra roll!\n" + 
			      "You can recieve a maximum of one extra roll per round.";
		condition = () -> Boolean.valueOf(Combo.numSpecialsByType(p.getDie(), 1) >= 1);
		exec = () -> p.incrementMaxRolls();
	}
	/**
	 * Getter for associated faction enum
	 */
	public Faction getFactionType(){
		return Faction.STARKS;
	}
}
