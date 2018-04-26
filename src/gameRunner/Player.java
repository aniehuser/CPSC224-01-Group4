package gameRunner;

import java.util.ArrayList;
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
//	private final IFaction faction;
	
	
	private final int sides; // sides of dice
	private final int die; // number of die
	private int rolls; // current number of rolls
	private final int maxRolls; // max number of rolls
	private int rounds; // current number of rounds
	private final int maxRounds; // max rounds in a game
	private String name; //contains a player's name
	
	/**
	 * Constructor
	 * @param sides sides of dice
	 * @param die number of die
	 * @param rolls rolls per round
	 */
	public Player(int sides, int die, int rolls, String name){
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
	}
	/**
	 * Execute a single round for this player object
	 * @deprecated
	 */
	public void doRound(){
		if(rounds == maxRounds){
			System.out.println("Error: Played too many rounds");
			return;
		}
		
		// Declare and Initialize variables 
		Hand hand = new Hand(sides,die,rolls);
		StringContainer inStr = new StringContainer();
		boolean goodInput = false;
		
		//shuffle and output hand once automatically
		hand.shuffleAll();
		System.out.println(hand.toString());
		
		//shuffle game.getRollsPerRound - 1 number of times
		for(int i=1; i<rolls; i++){
			// get input and shuffle hand accordingly
			goodInput = false;
			while(!goodInput){
				goodInput = InputHandler.dieToKeep(inStr);
			}
			// end loop when all input is y's
			if(inStr.getString().matches("[yY]{"+die+"}")){
				break;
			}
			// shuffle and display
			hand.shuffle(inStr.getString());
			System.out.println(hand.toString());
		}
		

		// create score object and calculate available scores
		Score score = new Score(hand);
		score.calculateScore();
		System.out.println(score.toString());
		
		// print score card
		System.out.println(toString());
		
		// get input for score to keep
		goodInput = InputHandler.scoreToKeep(inStr, scorecard);
		while(!goodInput){
			System.out.println("Invalid key or score has already been assigned, try again");
			goodInput = InputHandler.scoreToKeep(inStr, scorecard);
		}
		
		// set score and recalculate totals 
		scorecard.put(inStr.getString(), score.getScore(inStr.getString()));
		calculateTotals();
		
		//increment rounds
		rounds++;
	}
	public void rollInit(){
		if(rolls != 0){
			System.out.println("Roll Error: Cannot init started round");
			return;
		}
		hand = new Hand(sides, die, maxRolls);
		hand.shuffleAll();
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
		scorecard.put(key, scorer.getScore(key));
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
    /**
     * Clears toggle buttons so users can make a new choice on what die to keep
     * use this method in between rolls
     *
     */
    public ArrayList<String> generateScoreList(){
        ArrayList<String> currentScoreCard = new ArrayList<>();
        for(int i=1; i<=sides; i++){
            int v = scorecard.get(Integer.toString(i));
            currentScoreCard.add("Score " + ((v < 0) ? "nil" : v) + " on the " + i +" line.\n");
        }
        for(int i=0; i<lowerScoreTypes.length; i++) {
            int v = scorecard.get(lowerScoreTypes[i]);
            currentScoreCard.add("Score " + ((v < 0) ? "nil" : v) + " on the " + lowerScoreTypes[i] + " line.\n");
        }
        return currentScoreCard;
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
