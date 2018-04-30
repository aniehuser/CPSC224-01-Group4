package factions;

import gameRunner.Combo;
import gameRunner.Player;

/**
 * Extends BaseFaction and specifies conditions and executions for Baratheon
 * faction.
 * 
 * CPSC 224-01, Spring 2018
 * Final Project
 * 
 * @author Anthony Niehuser
 *
 * @version v1.0  4/29/2018
 */
public class BaratheonFaction extends BaseFaction {
	/**
	 * Base constructor, sets condition, message, and exec for BaseFaction
	 * @param p reference to player associated with faction
	 */
	public BaratheonFaction(Player p) {
		super(p);
		message = "If you roll 3 Kingsgaurd Knights, add 10 pts to this hand's Score!";
		condition = () -> Boolean.valueOf(Combo.maxOfGivenKindFound(2, p.getDie()) >= 3);
		exec = () -> p.setBonusPoints(10);
	}
	
	/**
	 * resets faction special roll
	 */
	public void resetFaction(){
		super.resetFaction();
		p.setBonusPoints(0);
	}

	/**
	 * Getter for associated faction enum
	 */
	public Faction getFactionType(){
		return Faction.BARATHEON;
	}
}
