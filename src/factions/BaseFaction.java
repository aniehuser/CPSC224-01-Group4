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
	protected Player p;
	protected String message; 
	protected boolean executed;
	protected Supplier<Boolean> condition;
	protected Runnable exec;
	
	public BaseFaction(Player p){
		this.p = p;
		executed = false;
	}
	
	public boolean isSpecialHand(){
		return condition.get().booleanValue();
	}
	
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
	
	public void resetFaction(){
		executed = false;
	}
	
	public String specialInstructions(){
		return message;
	}
	
	public boolean isExecuted(){
		return executed;
	}
	public abstract Faction getFactionType();
}
