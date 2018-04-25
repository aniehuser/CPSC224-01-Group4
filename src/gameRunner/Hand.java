package gameRunner;
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
	private final int specialOdds[] = {500, 400, 300, 200, 100, 75, 50};
	private final int specialRange = 10000;
	private int dieSides;
	private Die[] rolls;
	private int rollNum;
	private int maxRolls;


	/**
	 * Default Constructor
	 */
	public Hand() {
		rollNum = 0;
		rolls = new Die[5];
		for (Die d : rolls) d = new Die();
		dieSides = 6;
		maxRolls = 3;
	}

	/**
	 * Constructor to specify number of die to roll and
	 * number of sides per die.
	 *
	 * @param dieNum   Number of die to roll
	 * @param dieSides Number of sides on a die
	 */
	public Hand(int dieSides, int dieNum, int maxRolls) {
		rollNum = 0;
		rolls = new Die[dieNum];
		for (int i = 0; i < rolls.length; i++) rolls[i] = new Die();
		this.dieSides = dieSides;
		this.maxRolls = maxRolls;
	}

	/**
	 * Deep Copy Constructor
	 *
	 * @param other Hand object to copy
	 */
	public Hand(Hand other) {
		this.dieSides = other.dieSides;
		this.rollNum = other.rollNum;
		this.rolls = new Die[other.rolls.length];
		for (int i = 0; i < other.rolls.length; i++) {
			rolls[i] = other.rolls[i].clone();
		}
	}

	/**
	 * Testing constructor
	 */
	public Hand(int dieSides, int dieNum, int maxRolls, int[] types, boolean[] specials) {
		rollNum = 0;
		rolls = new Die[dieNum];
		for (int i = 0; i < rolls.length; i++) rolls[i] = new Die(types[i], specials[i]);
		this.dieSides = dieSides;
		this.maxRolls = maxRolls;
	}

	/**
	 * Getter for number of side on a die.
	 *
	 * @return number of die sides
	 */
	public int getDieSides() {
		return dieSides;
	}

	/**
	 * Getter for reference to roll array.
	 *
	 * @return array of roll values
	 */
	public Die[] getRolls() {
		//TODO:: deep copy
		return rolls;
	}

	/**
	 * ReRoll dice specified by input string.
	 *
	 * @param toKeep must be string of length 5 where y's specify keep value
	 *               and n's specify re-roll value.
	 * @deprecated
	 */
	public void shuffle(String toKeep) {
		// prevent from rolling more than three times
		if (rollNum > maxRolls - 1) {
			System.out.println("Cannot roll more than " + maxRolls + " times!");
			return;
		}
		// loop through input string to keep/reroll valeus
		toKeep = toKeep.toLowerCase();
		for (int i = 0; i < rolls.length; i++) {
			if (toKeep.charAt(i) == 'n')
				roll(rolls[i]);
		}
		rollNum++;
	}
	public Die getDieByIndex(int i){
		return rolls[i];
	}
	public void setDieByIndex(int i, Die d){
		rolls[i] = d.clone();
	}
			

	/**
	 * Input boolean array to indicate which dice in a hand to shuffle and
	 * which dice to keep the same
	 *
	 * @param toKeep boolean array, false elements are rerolled, true elements are not
	 * @return true if successful, false if not successful
	 */
	public boolean shuffle(boolean[] toKeep) {
		// prevent from rolling more than three times
		if (rollNum > maxRolls - 1) {
			System.out.println("Hand Error: Cannot roll more than " + maxRolls + " times!");
			return false;
		}
		for (int i = 0; i < rolls.length; i++) {
			if (!toKeep[i])
				roll(rolls[i]);
		}
		rollNum++;
		return true;
	}

	/**
	 * Reroll all dice in hand
	 */
	public void shuffleAll() {
		// prevent from rolling more than three times
		if (rollNum > maxRolls - 1) {
			System.out.println("Cannot roll more than " + maxRolls + " times!");
			return;
		}
		// loop through input string to keep/reroll valeus
		for (int i = 0; i < rolls.length; i++) {
			roll(rolls[i]);
		}
		rollNum++;
	}

	/**
	 * Generates a random number inclusive between 1 and numDieSides (6)
	 *
	 * @return random integer beteween 1 and 6
	 */
	private void roll(Die die) {
		int newType = gen.nextInt(dieSides);
		die.setType(newType + 1, isSpecial(newType));
	}

	private boolean isSpecial(int type) {
		return specialOdds[type] > gen.nextInt(specialRange);
	}

	/**
	 * Returns string of roll values
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Current Hand: ");
		for (int i = 0; i < rolls.length; i++) {
			buffer.append(rolls[i].getType() + " " + rolls[i].isSpecial() + "  ");
		}
		return buffer.toString();
	}

	/**
	 * Make deep copy of array to rolls field
	 *
	 * @param in array to deep copy
	 */
	public void setRolls(Die[] in) {
		// TODO::make sure this is deep copy
		rolls = (Die[]) in.clone();
	}

	/**
	 * Create deep copy of this hand object
	 */
	public Hand clone() {
		//TODO::make sure is deep copy
		Hand ret;
		try {
			ret = (Hand) super.clone();
			ret.setRolls(this.rolls);
		} catch (CloneNotSupportedException e) {
			ret = null;
			System.out.println("error: " + e.getMessage());
		}
		return ret;
	}

	public int getRollNum() {
		return rollNum;
	}
}