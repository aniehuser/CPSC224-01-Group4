package factions;

import gameRunner.Combo;
import gameRunner.Player;

/**
 * Extends BaseFaction and specifies conditions and executions for Targaryen
 * faction.
 * 
 * CPSC 224-01, Spring 2018
 * Final Project
 * 
 * @author Anthony Niehuser
 *
 * @version v1.0  4/29/2018
 */
public class TargaryenFaction extends BaseFaction {
	/**
	 * Base constructor, sets condition, message, and exec for BaseFaction
	 * @param p reference to player associated with faction
	 */
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
	/**
	 * Getter for associated faction enum
	 */
	public Faction getFactionType(){
		return Faction.TARGARYEN;
	}
}
