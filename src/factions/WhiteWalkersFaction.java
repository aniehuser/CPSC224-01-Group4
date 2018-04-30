package factions;

import java.util.Arrays;

import gameRunner.Combo;
import gameRunner.Player;
import gameRunner.Die;

/**
 * Extends BaseFaction and specifies conditions and executions for WhiteWalkers
 * faction.
 * 
 * CPSC 224-01, Spring 2018
 * Final Project
 * 
 * @author Anthony Niehuser
 *
 * @version v1.0  4/29/2018
 */
public class WhiteWalkersFaction extends BaseFaction {
	/**
	 * Base constructor, sets condition, message, and exec for BaseFaction
	 * @param p reference to player associated with faction
	 */
	public WhiteWalkersFaction(Player p) {
		super(p);
		message = "If you roll the Night King, all Night's Watch rangers become Undead!\n" + 
				  "Effect can occur once per round.";
		condition = () -> Boolean.valueOf(Combo.numSpecialsByType(p.getDie(), 6) >= 1);
		exec = () -> {
			for(Die d : Arrays.asList(p.getDie())){
				if(d.getType() == 1){
					d.setType(4, d.isSpecial());
				}
			}
		};
	}
	/**
	 * Getter for associated faction enum
	 */
	public Faction getFactionType(){
		return Faction.WHITE_WALKERS;
	}
}
