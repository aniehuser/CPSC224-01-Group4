package gameRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class that reads and writes from config file and
 * stores values to be used in game
 * 
 * CPSC 224-01, Spring 2018
 * Programming Assignment #6
 * 
 * @author Anthony Niehuser
 * 
 * @version v1.0 3/21/2018
 */
public class Game {
	// Minimum values for sides, die, and rolls
	public final int MIN_SIDES = 5;
	public final int MIN_DIE = 5;
	public final int MIN_ROLLS = 2;
	
	// path to config file in project
	private final String config = "yahtzeeConfig.txt";
	
	private Scanner file = null;

	private int sides;//number of sides on a die
	private int die; //number of die
	private int rolls;//number of rolls in a round
	private int maxRounds;//max rounds to be played in a game
	private int rounds;//current round in a given game instance
	private boolean validInstance;//true if Game object properly set up
	private boolean updatedConfig;//true if Game variables changed in update
	
	/**
	 * Default Constructor
	 */
	public Game(){
		validInstance = false;
		updatedConfig = false;
	}
	/**
	 * Load config file and initialize values to member variables 
	 */
	public void start() {
		try{
			//open file and initialize values
			file = new Scanner(new File(config));
			sides = file.nextInt();
			die = file.nextInt();
			rolls = file.nextInt();
			rounds = 0;
			maxRounds = sides + 9;
			file.close();
			
			//check if values are valid
			if(!validConfig(sides, die, rolls)){
				System.out.println("Invalid config file values.");
				validInstance = false;
			} else {
				validInstance = true;
			}
		} catch(FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
			if(file != null){
				file.close();
			}
			validInstance = false;
		}
	}
	/**
	 * Method checks that the three config value are valid
	 * @param s  number of sides on a dice
	 * @param d  number of die
	 * @param r  number of rolls per hand
	 * @return  true if parmaters are valid, false otherwise
	 */
	private boolean validConfig(int s, int d, int r){
		return (s >= MIN_SIDES) &&
				(d >= MIN_DIE) && 
				(r >= MIN_ROLLS);
	}
	/**
	 * Getter for number of sides on a dice
	 * @return integer of sides
	 */
	public int getDieSides(){
		return sides;
	}
	/**
	 * Getter for number of die to roll
	 * @return integer of die number
	 */
	public int getDieNum(){
		return die;
	}
	/**
	 * Getter for number of rolls in a round
	 * @return
	 */
	public int getRollsPerRound(){
		return rolls;
	}
	/**
	 * Getter for current round
	 * @return int of current round number
	 */
	public int getCurrentRound(){
		return rounds;
	}
	/**
	 * Getter for maximum number of rounds
	 * @return int of maximum number of rounds
	 */
	public int getMaxRounds(){
		return maxRounds;
	}
	/**
	 * Increment the current round number by one
	 */
	public void incrementRound(){
		rounds++;
	}
	/**
	 * Check externally if Game object is valid
	 * @return true if valid, false otherwise
	 */
	public boolean isValidInstance(){
		return validInstance;
	}
	/**
	 * Update game parmaeters from user input
	 */
	public void update(){
		// check if object is valid before updating
		if(!validInstance){
			System.out.println("Cannot update invalid Game instance");
			return;
		}
		updatedConfig = true;
		StringContainer value = new StringContainer();
		
		// update sides
		boolean goodInput = false;
		while(!goodInput){
			goodInput = InputHandler.newSides(value);
		}
		sides = Integer.parseInt(value.getString());
		
		// update die
		goodInput = false;
		while(!goodInput){
			goodInput = InputHandler.newDie(value);
		}
		die = Integer.parseInt(value.getString());
		
		//update rolls
		goodInput = false;
		while(!goodInput){
			goodInput = InputHandler.newRolls(value);
		}
		rolls = Integer.parseInt(value.getString());
		
		maxRounds = sides + 7;
	}
	/**
	 * Updates config file if game parameters are changed
	 */
	public void end() {
		// end if not updated
		if(!updatedConfig){
			return;
		}
		
		// write to file
		FileWriter writer = null;
		try {
			writer = new FileWriter(config);
			writer.write(sides + "\n" + die + '\n' + rolls + '\n');
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
	}
	/**
	 * Returns string in the form:
	 * "you are playing with {die} {sides}-sided dice
	 * you get {rolls} per hand"
	 */
	public String toString(){
		StringBuffer out = new StringBuffer();
		out.append("\nyou are playing with " + die + " " + sides + "-sided dice\n");
		out.append("you get " + rolls + " rolls per hand");
		return out.toString();
	}

	public boolean isGameOver(){
		return rounds < maxRounds;
	}
}
