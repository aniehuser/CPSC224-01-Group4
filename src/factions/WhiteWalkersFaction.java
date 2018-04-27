package factions;

import java.util.Arrays;

import gameRunner.Combo;
import gameRunner.Player;
import gameRunner.Die;

public class WhiteWalkersFaction extends BaseFaction {

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

	public Faction getFactionType(){
		return Faction.WHITE_WALKERS;
	}
}
