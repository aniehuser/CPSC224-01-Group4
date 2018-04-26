package factions;

import java.util.Arrays;

import gameRunner.Combo;
import gameRunner.Player;
import gameRunner.Die;

public class GreyJoysFaction extends BaseFaction {
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
}
