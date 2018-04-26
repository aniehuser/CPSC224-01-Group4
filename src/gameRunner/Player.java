package gameRunner;

import java.util.Hashtable;
import factions.*;
//comment
/**
 * 
 * @author Anthony Niehuser
 * @version 2.0
 */
public class Player {
	// non-int score card keys
	private final String[] lowerScoreTypes = {"Upper Total", "3 of a Kind","4 of a Kind","Full House",
			"The North","The South","Easteros","The Dead","The Crown","The Others","Dragons","Yahtzee", "Lower Total", "Grand Total"};
	private Hashtable<String,Integer> scorecard; // store all value of score card. -1 means unnassigned
	private Hand hand;
	private Score scorer;
	private final BaseFaction faction;
	
	
	private final int sides; // sides of dice
	private final int die; // number of die
	private int rolls; // current number of rolls
	private int maxRolls; // max number of rolls
	private int rounds; // current number of rounds
	private int maxRounds; // max rounds in a game
	private String name; //contains a player's name
	private int bonusPoints = 0; // only used for special roll with BaratheonFaction
	
	/**
	 * Constructor
	 * @param sides sides of dice
	 * @param die number of die
	 * @param rolls rolls per round
	 */
	public Player(int sides, int die, int rolls, String name, Faction faction){
		this.rounds = 0;
		this.sides = sides;
		this.die = die;
		this.maxRolls = rolls;
		this.rolls = 0;
		this.scorecard = initDict(); 
		this.hand = new Hand(sides,die,rolls);
		this.scorer = new Score();
		this.maxRounds = scorecard.size() - 3;
		this.name = name;
		this.faction = initFaction(faction);
	}
	public BaseFaction initFaction(Faction f){
		switch(f){
		case STARKS:
			return new StarksFaction(this);
		case LANNISTERS:
			return new LannistersFaction(this);
		case TARGARYEN:
			return new TargaryenFaction(this);
		case WHITE_WALKERS:
			return new WhiteWalkersFaction(this);
		case BARATHEON:
			return new BaratheonFaction(this);
		case GREYJOYS:
			return new GreyJoysFaction(this);
		case CHILDREN_OF_THE_FOREST:
			return new ChildrenOfTheForestFaction(this);
		default:
			System.out.println("Faction not yet implemented");
			return new StarksFaction(this);
		}
	}
	/**
	 * CARL DO NOT USE THIS METHOD
	 */
	public void incrementMaxRolls(){
		maxRolls++;
		hand.incrementMaxRolls();
	}
	public void incrementMaxRounds(){
		maxRounds++;
	}
	
	public void rollInit(){
		if(rolls != 0){
			System.out.println("Roll Error: Cannot init started round");
			return;
		}
		hand = new Hand(sides, die, maxRolls);
		hand.shuffleAll();
		
		if(faction.isSpecialHand()){
			faction.executeSpecial();
		}
		rolls++;
	}
	public void rollOnce(boolean[] keep){
		if(keep.length != die){
			System.out.println("Roll Error: Bad input length");
			return;
		}
		if(isPlayerTurnsOver()){
			System.out.println("Roll Error: Too many rounds");
			return;
		}
		if(isRoundOver()){
			System.out.println("Roll Error: Too many rolls");
			return;
		}
		
		if(isAllTrue(keep)){
			System.out.println("Keep all die, end round");
			rolls = maxRolls-1;
		} else if(!hand.shuffle(keep)){
			System.out.print("Roll Error: ");
			return;
		}
		rolls++;
	
		if(faction.isSpecialHand()){
			faction.executeSpecial();
		}
		
		if(isRoundOver()){
			scorer.calculateScore(hand);
			rounds++;
		}
		
	}
	public Score getScorer(){
		if(!isRoundOver()){
			System.out.println("Score Error: Cannot score until round is over");
			return null;
		}
		return scorer;
	}

	public Hashtable<String, Integer> getScoreCard(){
		return scorecard;
	}
	public Hand getHand(){
		return hand;
	}
	public Die[] getDie(){
		return hand.getRolls();
	}
	public int getRolls(){
		return rolls;
	}
	public void setScore(String key){
		if(!isRoundOver()){
			System.out.println("Score Error: Cannot score until round is over");
			return;
		}
		if(!isScoreSet(key)){
			System.out.println("Score Error: Score has already been set");
			return;
		}
		scorecard.put(key, bonusPoints + scorer.getScore(key));
		faction.resetFaction();
		rolls = 0;
	}
	public boolean isScoreSet(String key){
		StringContainer inStr = new StringContainer(key);
		return InputHandler.scoreToKeep(inStr, scorecard);
	}

	private boolean isAllTrue(boolean[] array){
		boolean yes = true;
		for(int i=0; i<array.length; i++){
			if(!array[i]){
				yes = false;
				break;
			}
		}
		return yes;
	}
	/**
	 * Calculate the upper, lower and grand totals
	 */
	private void calculateTotals() {
		scorecard.put("Upper Total", sumUpper());
		scorecard.put("Lower Total", sumLower());
		scorecard.put("Grand Total", scorecard.get("Upper Total") + scorecard.get("Lower Total"));
	}
	/**
	 * Calculate total score of upper score card
	 * @return  int of sum of upper score card
	 */
	private int sumUpper(){
		int sum = 0;
		for(int i=1; i<=sides; i++){
			String k = Integer.toString(i);
			int v = scorecard.get(k);
			// check if v assigned
			if(v >= 0){
				sum += v; 
			}
		}
		return sum;
	}
	/**
	 * Calculate total score of lower score card
	 * @return int of suem of lower score card
	 */
	private int sumLower() {
		int sum = 0;
		for(int i = 1; i<lowerScoreTypes.length-2; i++){
			int v = scorecard.get(lowerScoreTypes[i]);
			// check that v has been assigned
			if(v >= 0){
				sum += v;
			}
		}
		return sum;
	}
	/**
	 * Initialize scores Hashtable with keys specified in scoreType
	 * @return Hashtable<String,Integer> with values of -1
	 */
	private Hashtable<String,Integer> initDict(){
		Hashtable<String,Integer> d = new Hashtable<String,Integer>();
		//initialize upper score card values
		for(int i=1; i<=sides; i++){
			d.put(Integer.toString(i), -1);
		}
		//initialize lower score card
		for(int i=0; i<lowerScoreTypes.length; i++){
			d.put(lowerScoreTypes[i], -1);
		}
		// set totals to 0
		d.put("Lower Total", 0);
		d.put("Upper Total", 0);
		d.put("Grand Total", 0);
		return d;
	}
	public void setBonusPoints(int a){
		bonusPoints = a;
	}
	/**
	 * return string representation of current score card
	 */
	public boolean isRoundOver(){
		return rolls == maxRolls;
	}
	
	public boolean isPlayerTurnsOver(){
		return rounds == maxRounds;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
		StringBuffer b = new StringBuffer();
		b.append("Current Score Card:\n");
		for(int i=1; i<=sides; i++){
			int v = scorecard.get(Integer.toString(i));
			b.append("Score " + ((v < 0) ? "nil" : v) + " on the " + i +" line.\n");
		}
		for(int i=0; i<lowerScoreTypes.length; i++) {
			int v = scorecard.get(lowerScoreTypes[i]);
			b.append("Score " + ((v < 0) ? "nil" : v) + " on the " + lowerScoreTypes[i] + " line.\n");
		}
		return b.toString();
	}
}
