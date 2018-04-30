package factions;
import java.util.Arrays;

import gameRunner.Combo;
import gameRunner.Die;
import gameRunner.Player;

/**
 * Extends BaseFaction and specifies conditions and executions for Lannisters
 * faction.
 * 
 * CPSC 224-01, Spring 2018
 * Final Project
 * 
 * @author Anthony Niehuser
 *
 * @version v1.0  4/29/2018
 */
public class LannistersFaction extends BaseFaction {
	/**
	 * Base constructor, sets condition, message, and exec for BaseFaction
	 * @param p reference to player associated with faction
	 */
	public LannistersFaction(Player p){
		super(p);
		message = "If you roll Jaime Lannister AND Cersei Lannister, change all\n" +
				  "non-rare rolls to Wildfire.";
		condition = () -> Boolean.valueOf(Combo.numSpecialsByType(p.getDie(), 2) >=1 &&
							Combo.numSpecialsByType(p.getDie(), 5) >=1);
		exec = () -> {
			for(Die d : Arrays.asList(p.getDie())){
				if(!d.isSpecial()){
					d.setType(5, false);
				}
			}
		};
	}
	/**
	 * Getter for associated faction enum
	 */
	public Faction getFactionType(){
		return Faction.LANNISTERS;
	}
}
