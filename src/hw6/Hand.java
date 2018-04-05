package hw6;
import java.util.Random;

/**
 * Class to describe a hand. Performs shuffle capabilities
 * and stores role values.
 * 
 * CPSC 224-01, Spring 2018
 * Programming Assignment #6
 * 
 * @author Anthony Niehuser
 * 
 * @version v1.1 3/19/2018
 */
public class Hand implements Cloneable {
	private final Random gen = new Random();
	private int dieSides;
	private int[] rolls;
	private int rollNum;
	private int maxRolls;
	
	/**
	 * Default Constructor
	 */
	public Hand(){
		rollNum = 0;
		rolls = new int[5];
		dieSides = 6;
		maxRolls = 3;
	}
	/**
	 * Constructor to specify number of die to roll and 
	 * number of sides per die.
	 * 
	 * @param dieNum Number of die to roll
	 * @param dieSides Number of sides on a die
	 */
	public Hand(int dieSides, int dieNum, int maxRolls) {
		rollNum = 0;
		rolls = new int[dieNum];
		this.dieSides=dieSides;
		this.maxRolls = maxRolls;
	}
	/**
	 * Deep Copy Constructor
	 * @param other Hand object to copy
	 */
	public Hand(Hand other){
		this.dieSides = other.dieSides;
		this.rollNum = other.rollNum;
		this.rolls = new int[other.rolls.length];
		for(int i=0; i<other.rolls.length; i++){
			rolls[i] = other.rolls[i];
		}
	}
	/**
	 * Getter for number of side on a die.
	 * @return number of die sides
	 */
	public int getDieSides(){
		return dieSides;
	}
	/**
	 * Getter for reference to roll array.
	 * @return array of roll values
	 */
	public int[] getRolls(){
		return rolls;
	}
	/**
	 * ReRoll dice specified by input string.
	 * @param toKeep must be string of length 5 where y's specify keep value
	 * 		and n's specify re-roll value.
	 */
	public void shuffle(String toKeep){
		// prevent from rolling more than three times
		if(rollNum > maxRolls-1){
			System.out.println("Cannot roll more than " + maxRolls + " times!");
			return;
		}
		// loop through input string to keep/reroll valeus
		toKeep = toKeep.toLowerCase();
		for(int i=0; i<rolls.length; i++){
			if(toKeep.charAt(i) == 'n')
				rolls[i] = roll();
		}
		rollNum++;
	}
	/**
	 * Reroll all dice in hand
	 */
	public void shuffleAll(){
		// prevent from rolling more than three times
		if(rollNum > maxRolls-1){
			System.out.println("Cannot roll more than " + maxRolls + " times!");
			return;
		}
		// loop through input string to keep/reroll valeus
		for(int i=0; i<rolls.length; i++){
			rolls[i] = roll();
		}
		rollNum++;
	}
	
	/**
	 * Generates a random number inclusive between 1 and numDieSides (6)
	 * @return random integer beteween 1 and 6
	 */
	private int roll(){
		return gen.nextInt(dieSides) + 1;
	}

	/**
	 * Returns string of roll values
	 */
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Current Hand: ");
		for(int i=0; i<rolls.length; i++){
			buffer.append(rolls[i] + " ");
		}
		return buffer.toString();
	}
	/**
	 * Make deep copy of array to rolls field
	 * @param in array to deep copy
	 */
	public void setRolls(int[] in){
		rolls = (int[]) in.clone();
	}
	/**
	 * Create deep copy of this hand object
	 */
	public Hand clone() {
		Hand ret;
		try{
			ret = (Hand) super.clone();
			ret.setRolls(this.rolls);
		} catch(CloneNotSupportedException e){
			ret = null;
			System.out.println("error: " + e.getMessage());
		}
		return ret;
	}
}
