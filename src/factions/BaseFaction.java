package factions;

import gameRunner.Player;

public abstract class BaseFaction {
	protected Player p;
	protected String message; 
	protected boolean executed;
	
	public BaseFaction(Player p){
		this.p = p;
		executed = false;
	}
	
	public abstract boolean isSpecialHand();
	
	public void executeSpecial(){
		if(executed){
			return;
		}
		executed = true;
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
