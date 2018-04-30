package factions;

import java.util.Arrays;

import gameRunner.Combo;
import gameRunner.Player;
import gameRunner.Die;

/**
 * Extends BaseFaction and specifies conditions and executions for GreyJoys
 * faction.
 * 
 * CPSC 224-01, Spring 2018
 * Final Project
 * 
 * @author Anthony Niehuser
 *
 * @version v1.0  4/29/2018
 */
public class GreyJoysFaction extends BaseFaction {
	/**
	 * Base constructor, sets condition, message, and exec for BaseFaction
	 * @param p reference to player associated with faction
	 */
	public GreyJoysFaction(Player p) {
		super(p);
		message = "Roll 2 Arya Starks and all Faceless Men are now worth 6 points!";
		condition = () -> Boolean.valueOf(Combo.numSpecialsByType(p.getDie(), 3) >= 2);
		exec = () -> {
			for(Die d : Arrays.asList(p.getDie())){
				if(d.getType() == 3){
					d.setValue(6);
				}
			}
		};
	}
	
	/**
	 * Getter for associated faction enum
	 */
	public Faction getFactionType(){
		return Faction.GREYJOYS;
	}

}
