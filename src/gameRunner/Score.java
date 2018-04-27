package gameRunner;

import java.util.Arrays;
import java.util.Hashtable;

/**
 * Class to calculate and describe scores available to choose.
 * TODO:: Choose one score and apply to scorecard 
 * 
 * CPSC 224-01, Spring 2018
 * Programming Assignment #6
 * 
 * @author Anthony Niehuser
 * 
 * @version v1.1 3/19/2018
 */
public class Score {
	//constants and dictionary keys
	private final String[] lowerScoreTypes = {"3 of a Kind","4 of a Kind","Full House","The North",
						"The South","Easteros","The Dead","The Crown","The Others","Dragons","Yahtzee"};
	private final int[] BASE_MULTIPLIER = {2,4,6,8,10,12,14};
	private final int FULL_HOUSE = 50;
	private final int NORTH = 30;
	private final int SOUTH = 35;
	private final int EASTEROS = 40;
	private final int DEAD = 45;
	private final int CROWN = 50;
	private final int OTHERS = 55;
	private final int DRAGONS = 60;
	private final int YAHTZEE = 100;

	private int[] specialCount = {0,0,0,0,0,0,0};
	private Hashtable<String,Integer> scores;//Store possible scores
	private Hand hand;	//Store hand info
	
	/**
	 * Constructor
	 * @param hand to determine what scores are available
	 * @deprecated
	 */
	public Score(Hand hand){
		this.hand = hand;
		scores = initDict();
	}
	public Score(){
		this.hand = null;
		scores = initDict();
	}
	/**
	 * Initialize scores Hashtable with keys specified in scoreType
	 * @return Hashtable<String,Integer> with values of 0
	 */
	private Hashtable<String,Integer> initDict(){
		Hashtable<String,Integer> d = new Hashtable<String,Integer>();
		//initialize upper score card values
		for(int i=1; i<=BASE_MULTIPLIER.length; i++){
			d.put(Integer.toString(i), 0);
		}
		//initialize lower score card
		for(int i=0; i<lowerScoreTypes.length; i++){
			d.put(lowerScoreTypes[i], 0);
		}
		return d;
	}
	/**
	 * Getter for hand
	 * @return
	 */
	public Hand getHand(){
		return hand;
	}
	/**
	 * Get a specified score key and get the appropriate score for the hand
	 * @param key  string value of key in dictionary
	 * @return integer value of key parameter
	 */
	public int getScore(String key){
		return scores.get(key);
	}

	/**
	 * Get a specified score key and get the appropriate score for the hand
	 * @param key  string value of key in dictionary
	 * @return integer value of key parameter
	 */
	public String generateScoreMessage(String key){
		return Integer.toString(scores.get(key)) + " on the " + key + " line";
	}
	public void calculateScore(Hand hand){
		this.hand = hand;
		//Sort hand and output to terminal
		sortHand();
		System.out.print("Here is your sorted hand: ");
		System.out.println(hand.toString());
		
		//Calculate scores
		calculateLowerScore();
	}
	/**
	 * Sort rolls in hand object. Maybe move to Hand class
	 */
	public void sortHand(){
		Arrays.sort(hand.getRolls());
	}
	/**
	 * Sum all values in rolls
	 * @return integer of sum 
	 */
	public int totalAllDice(){
	Die[] rolls = hand.getRolls();
		int sum = 0;
		for(int i=0; i<rolls.length; i++){
			sum += rolls[i].getValue();
		}
		return sum;
	}
	/**
	 * Calculate values for the upper score card. 
	 */
	public void calculateUpperScore(){
		Die[] rolls = hand.getRolls();
		for(int i=1; i<=hand.getDieSides(); i++){
			int values = 0; // count occurrences of roll number
			for(int j=0; j<rolls.length; j++){
				if(rolls[j].getType() == i){
					values += rolls[j].getValue();
					if(rolls[j].isSpecial()){
						specialCount[i-1]++;
					}
				}
			}
			int multiply = (specialCount[i-1]==0) ? 1 : specialCount[i-1] * BASE_MULTIPLIER[i-1];
			scores.put(Integer.toString(i), values * multiply);
		}
	}
	public int getMultiplierByType(int type){
		if(type > specialCount.length || type < 1){
			System.out.println("Error: Invalid Type Input");
		}
		return specialCount[type-1];
	}
	
	public void setMultiplierByType(int type, int newVal){
		if(type > specialCount.length || type < 1){
			System.out.println("Error: Invalid Type Input");
		}
		specialCount[type-1] = newVal;
	}
	
	/**
	 * Calculates values of upper score card first, then lower score card
	 * combos.
	 */
	public void calculateLowerScore(){
		// Calculate upper score card
		calculateUpperScore();
		
		// Establish roll's attributes
		int dieTotal = totalAllDice();
		int maxOfKind = Combo.maxOfAKindFound(hand.getRolls());
		boolean fullHouse = Combo.fullHouseFound(hand.getRolls());
		boolean north = Combo.northFound(hand.getRolls());
		boolean south = Combo.southFound(hand.getRolls());
		boolean east = Combo.easterosFound(hand.getRolls());
		boolean dead = Combo.deadFound(hand.getRolls());
		boolean crown = Combo.crownFound(hand.getRolls());
		boolean other = Combo.othersFound(hand.getRolls());
		boolean dragons = Combo.dragonsFound(hand.getRolls());
		
//		// Assign relevant values to Hashtable
		if(maxOfKind >= 3) scores.put(lowerScoreTypes[0], dieTotal); // 3 of a kind
		if(maxOfKind >= 4) scores.put(lowerScoreTypes[1], dieTotal); // 4 of a kind
		if(fullHouse) scores.put(lowerScoreTypes[2], FULL_HOUSE); // full house
		if(north) scores.put(lowerScoreTypes[3], NORTH);
		if(south) scores.put(lowerScoreTypes[4], SOUTH);
		if(east) scores.put(lowerScoreTypes[5], EASTEROS);
		if(dead) scores.put(lowerScoreTypes[6], DEAD);
		if(crown) scores.put(lowerScoreTypes[7], CROWN);
		if(other) scores.put(lowerScoreTypes[8], OTHERS);
		if(dragons) scores.put(lowerScoreTypes[9], DRAGONS);
		if(maxOfKind >= 7) scores.put(lowerScoreTypes[10], YAHTZEE); // yahtzee
	}
	
	
	// return string of dictionary keys and values
	public String toString(){
		StringBuffer b = new StringBuffer();
		for(int i=1; i<=hand.getDieSides(); i++){
			b.append("Score " + scores.get(Integer.toString(i)) + " on the " + i +" line.\n");
		}
		for(int i=0; i<lowerScoreTypes.length; i++) 
			b.append("Score " + scores.get(lowerScoreTypes[i]) + " on the " + lowerScoreTypes[i] + " line.\n");
		return b.toString();
	}
	
}
