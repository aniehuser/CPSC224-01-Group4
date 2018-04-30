package factions;

import java.util.Arrays;

import gameRunner.Combo;
import gameRunner.Player;
import gameRunner.Die;

/**
 * Extends BaseFaction and specifies conditions and executions for Children
 * of the Forest Faction.
 * 
 * CPSC 224-01, Spring 2018
 * Final Project
 * 
 * @author Anthony Niehuser
 *
 * @version v1.0  4/29/2018
 */
public class ChildrenOfTheForestFaction extends BaseFaction {
	/**
	 * Base constructor, sets condition, message, and exec for BaseFaction
	 * @param p reference to player associated with faction
	 */
	public ChildrenOfTheForestFaction(Player p) {
		super(p);
		message = "Roll 4 White Walkers, turn one into the Night King!";
		condition = () -> Boolean.valueOf(Combo.maxOfGivenKindFound(6, p.getDie()) >= 4);
		exec = () -> {
			for(Die d : Arrays.asList(p.getDie())){
				if(d.getType() == 6 && !d.isSpecial()){
					d.setSpecial(true);
					break;
				}
			}
		};
	}

	/**
	 * Getter for associated faction enum
	 */
	public Faction getFactionType(){
		return Faction.CHILDREN_OF_THE_FOREST;
	}
}
