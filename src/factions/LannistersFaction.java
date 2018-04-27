package factions;
import java.util.Arrays;

import gameRunner.Combo;
import gameRunner.Die;
import gameRunner.Player;

public class LannistersFaction extends BaseFaction {

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

	public Faction getFactionType(){
		return Faction.LANNISTERS;
	}
}
