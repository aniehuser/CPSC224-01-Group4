package hw6;

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
	private final String[] lowerScoreTypes = {"3 of a Kind","4 of a Kind","Full House",
										"Small Straight","Large Straight","Yahtzee","Chance"};
	private final int BONUS = 35;
	private final int FULL_HOUSE = 25;
	private final int SM_STRAIGHT = 30;
	private final int LG_STRAIGHT = 40;
	private final int YAHTZEE = 50;
	private final int YAHTZEE_BONUS = 100;
	
	private Hashtable<String,Integer> scores;//Store possible scores
	private Hand hand;	//Store hand info
	
	/**
	 * Constructor
	 * @param hand to determine what scores are available
	 */
	public Score(Hand hand){
		this.hand = hand.clone();
		scores = initDict();
	}
	/**
	 * Initialize scores Hashtable with keys specified in scoreType
	 * @return Hashtable<String,Integer> with values of 0
	 */
	private Hashtable<String,Integer> initDict(){
		Hashtable<String,Integer> d = new Hashtable<String,Integer>();
		//initialize upper score card values
		for(int i=1; i<=hand.getDieSides(); i++){
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
	 * Calculate and populate values in scores given rolls in hand
	 */
	public void calculateScore(){
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
		int[] rolls = hand.getRolls();
		int sum = 0;
		for(int i=0; i<rolls.length; i++){
			sum += rolls[i];
		}
		return sum;
	}
	/**
	 * Calculate values for the upper score card. 
	 */
	public void calculateUpperScore(){
		int[] rolls = hand.getRolls();
		for(int i=1; i<=hand.getDieSides(); i++){
			int count = 0; // count occurences of roll number
			for(int j=0; j<rolls.length; j++){
				if(rolls[j] == i){
					count++;
				}
			}
			scores.put(Integer.toString(i), count * i);
		}
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
		int maxStraight = Combo.maxStraightFound(hand.getRolls());
		boolean fullHouse = Combo.fullHouseFound(hand.getRolls());
		
		// Assign relevant values to Hashtable
		if(maxOfKind >= 3) scores.put(lowerScoreTypes[0], dieTotal); // 3 of a kind
		if(maxOfKind >= 4) scores.put(lowerScoreTypes[1], dieTotal); // 4 of a kind
		if(fullHouse) scores.put(lowerScoreTypes[2], FULL_HOUSE); // full house
		if(maxStraight >= 4) scores.put(lowerScoreTypes[3], SM_STRAIGHT); // small straight
		if(maxStraight >= 5) scores.put(lowerScoreTypes[4], LG_STRAIGHT); // large straight
		if(maxOfKind >= 5) scores.put(lowerScoreTypes[5], YAHTZEE); // yahtzee
		scores.put(lowerScoreTypes[6], dieTotal); // chance
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
