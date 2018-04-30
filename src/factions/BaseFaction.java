package factions;

import gameRunner.Player;
import java.util.function.Supplier;
import java.lang.Runnable;

/**
 * Abstract class used to create underlying logic of special die rolls.
 * 
 * CPSC 224-01, Spring 2018
 * Final Project
 * 
 * @author anthonyniehuser
 *
 * @version v1.0  4/29/2018
 */
public abstract class BaseFaction {
	protected Player p;	// reference to the player that belongs to this faction
	protected boolean executed; // if the special rule has been executed in a round
	
	// following fields specified in extended class
	protected String message; 	// special rule in text
	protected Supplier<Boolean> condition;	// boolean statement which checks for special rule
	protected Runnable exec;	// executes special rule on player class
	
	/**
	 * Base constructor
	 * @param p reference to player of this faction
	 */
	public BaseFaction(Player p){
		this.p = p;
		executed = false;
	}
	
	/**
	 * Checks to see if a hand currently contains a special hand
	 * @return true if hand fulfills constraint, false otherwise
	 */
	public boolean isSpecialHand(){
		return condition.get().booleanValue();
	}
	
	/**
	 * Checks to makes sure a special can be executed, then does so.
	 */
	public void executeSpecial(){
		if(!isSpecialHand()){
			return;
		}
		if(executed){
			return;
		}
	
		executed = true;
		exec.run();
	}
	
	/**
	 * Resets a speical roll at the end of every round.
	 */
	public void resetFaction(){
		executed = false;
	}
	
	/**
	 * Returns the factions special instructions.
	 * @return
	 */
	public String specialInstructions(){
		return message;
	}
	
	/**
	 * Determines if a special roll has already occurred in this round.
	 * @return
	 */
	public boolean isExecuted(){
		return executed;
	}
	/**
	 * Returns given enum associated with subclass.
	 * @return
	 */
	public abstract Faction getFactionType();
}
