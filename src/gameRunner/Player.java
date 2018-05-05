package gameRunner;

import java.util.Hashtable;
import factions.*;


/**
 * Class represents a player in the Game, contains all high level logic for
 * performing a round and setting a score.
 * 
 * CPSC 224-01 Spring 2018
 * Final Project
 * 
 * @author Anthony Niehuser
 * 
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
	 * @param name Set a player's name
	 * @param faction Specify this player's faction
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
	/**
	 * Get a faction class from inputted enum
	 * @param f enum with with given faction 
	 * @return Class of type specified by faction
	 */
	public BaseFaction initFaction(Faction f){
		switch(f){
		case STARKS:return new StarksFaction(this);
		case LANNISTERS:return new LannistersFaction(this);
		case TARGARYEN:return new TargaryenFaction(this);
		case WHITE_WALKERS:return new WhiteWalkersFaction(this);
		case BARATHEON:return new BaratheonFaction(this);
		case GREYJOYS:return new GreyJoysFaction(this);
		case CHILDREN_OF_THE_FOREST:return new ChildrenOfTheForestFaction(this);
		default:
			System.out.println("Faction not yet implemented");
			return new StarksFaction(this);
		}
	}
	/**
	 * * CARL DO NOT USE THIS METHOD
	 * Increments a players max number of rolls for a given hand.
	 * Only to be used
	 */
	public void setMaxRolls(int n){
		maxRolls = n;
		hand.setMaxRolls(n);
	}

	public void setMaxRounds(int n){
		maxRounds = n;
	}

	/**
	 * Get players Faction
	 * @return Faction class
	 */
	public BaseFaction getFaction(){
		return faction;
	}

	/**
	 * Getter for current round.
	 * @return int value of current number of rounds
	 */
	public int getRounds(){
		return rounds;
	}

	/**
	 * Method used at the start of the round. Creates a new Hand and rolls all
	 * die.
	 */
	public void rollInit(){
		rolls = 0;
		hand = new Hand(sides, die, maxRolls);
		hand.shuffleAll();
		
		if(faction.isSpecialHand()){
			faction.executeSpecial();
		}
		rolls++;
	}
	/**
	 * Method use to perform every subsequent roll after rollInit(). Hand will not
	 * reroll after a round or a game is over. If all die are kept, sets round  to be over.
	 * @param keep boolean array that specifies which die to keep by index. True elements are
	 * kept, false elements are rerolled.
	 */
	public void rollOnce(boolean[] keep){
		// check for bad input
		if(keep.length != die){
			System.out.println("Roll Error: Bad input length");
			return;
		}
		// check that the game is not over
		if(isPlayerTurnsOver()){
			System.out.println("Roll Error: Too many rounds");
			return;
		}
		// check if round is over
		if(isRoundOver()){
			System.out.println("Roll Error: Too many rolls");
			return;
		}
		// if all die kept, end round
		if(isAllTrue(keep)){
			System.out.println("Keep all die, end round");
			rolls = maxRolls-1;
		// else, shuffle
		} else if(!hand.shuffle(keep)){
			System.out.print("Roll Error: ");
			return;
		}
		
		// increment rounds
		rolls++;
	
		// execute specialHand if hand meets special conditions
		if(faction.isSpecialHand()){
			faction.executeSpecial();
		}
		
		// calculate the score if round is over
		if(isRoundOver()){
			scorer.calculateScore(hand.clone());
			rounds++;
		}
	}
	/**
	 * Get Score class containing all possible scores for a given hand.
	 * @return Score class
	 */
	public Score getScorer(){
		return scorer;
	}
	/**
	 * Get a Players score by key value. Available keys are as follows:
	 * "1", "2", "3", "4"", "5", "6", "7", "Upper Total", "3 of a Kind",
	 * "4 of a Kind", "Full House", "The North", "The South", "Easteros",
	 * "The Dead", "The Crown", "The Others", "Dragons", "Yahtzee", "Lower Total", 
	 * "Grand Total"
	 * @param key String key value
	 * @return int value of score. Empty score is -1
	 */
	public int getPlayerScoreByKey(String key){
		return scorecard.get(key);
	}
	/**
	 * Getter for entire Scorecard Hashtable
	 * @return Hashtable of current scores
	 */
	public Hashtable<String, Integer> getScoreCard(){
		return scorecard;
	}
	/**
	 * Getter for current Hand class
	 * @return Hand object
	 */
	public Hand getHand(){
		return hand;
	}
	/**
	 * Getter for current player die values
	 * @return Array of Die
	 */
	public Die[] getDie(){
		return hand.getRolls();
	}
	/**
	 * Getter for current number of rolls
	 * @return int value for rolls
	 */
	public int getRolls(){
		return rolls;
	}
	/**
	 * Getter for max number of rolls
	 * @return int value for maxRolls
	 */
	public int getMaxRolls(){
		return maxRolls;
	}
	/**
	 * Set a score from the scorer and add it to the player's current score. 
	 * Scores are automatically calculated at the end of a round, and cannot be
	 * set to the scorecard until a round is over.
	 * Key values are as follows: "1", "2", "3", "4"", "5", "6", "7",
	 * "3 of a Kind", "4 of a Kind", "Full House", "The North", "The South",
	 * "Easteros", "The Dead", "The Crown", "The Others", "Dragons", "Yahtzee"
	 * @param key String value of key to set to 
	 */
	public void setScore(String key){
		// only set scores while round is over.
		if(!isRoundOver()){
			System.out.println("Score Error: Cannot score until round is over");
			return;
		}

		if(isScoreSet(key)){
			scorecard.put(key, bonusPoints + scorecard.get(key) + scorer.getScore(key));
			System.out.println("Score Error: Score has already been set");
		} else{
			scorecard.put(key, bonusPoints + scorer.getScore(key));
		}
		// calculate the player's current multiplier
		calculateTotals();
		
		// reset the round
		faction.resetFaction();
		rolls = 0;
	}
	/**
	 * Check if a given score is set. 
	 * @param key String of key value. keys are as follows: "1", "2", "3", "4"", "5", "6", "7",
	 * "3 of a Kind", "4 of a Kind", "Full House", "The North", "The South",
	 * "Easteros", "The Dead", "The Crown", "The Others", "Dragons", "Yahtzee"
	 * @return True if score has been set, false otherwise
	 */
	public boolean isScoreSet(String key){
		StringContainer inStr = new StringContainer(key);
		return !InputHandler.scoreToKeep(inStr, scorecard);
	}
	/**
	 * Determines if all elements in an array are true
	 * @param array to check if all elements are true.
	 * @return True if all elements are true, false otherwise
	 */
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
	/**
	 * DO NOT USE
	 * method used to set bonus points to add to a total rounds score. 
	 * This method is only intended to be used for special rolls and is
	 * handled in BaseFaction classes
	 * @param a points to add
	 */
	public void setBonusPoints(int a){
		bonusPoints = a;
	}
	public int getBonusPoints() {return bonusPoints; }
	/**
	 * Check if round is over.
	 * @return true if round over, false otherwise
	 */
	public boolean isRoundOver(){
		return rolls == maxRolls;
	}
	/**
	 * Check if a player has used all his/her turns
	 * @return return true if all turns have occurred, false otherwise
	 */
	public boolean isPlayerTurnsOver(){
		return rounds == maxRounds;
	}
	/**
	 * Getter for a player's name
	 * @return String value of player name
	 */
    public String getName() {
        return name;
    }
    /**
     * Setter for a Player's name.
     * @param name String value of name to set. 
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
	 * return string representation of current score card
	 */
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
