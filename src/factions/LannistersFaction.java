package factions;
import gameRunner.Combo;
import gameRunner.Player;

public class LannistersFaction extends BaseFaction {

	public LannistersFaction(Player p){
		super(p);
		message = "If you roll Jaime Lannister AND Cersei Lannister, play a bonus round\n" + 
				  "and sum that round to a chosen round. Unlimited bonus rounds.";
	}
	@Override
	public boolean isSpecialHand() {
		return Combo.numSpecialsByType(p.getDie(), 2) >=1 &&
				Combo.numSpecialsByType(p.getDie(), 5) >=1;
	}

	public void executeSpecial(){
		super.executeSpecial();
		p.incrementMaxRounds();
	}
}
