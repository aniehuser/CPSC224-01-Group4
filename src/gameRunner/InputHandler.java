package gameRunner;

import gameUI.TitleScreen;

import java.util.Hashtable;
import java.util.Scanner;

import static gameUI.TitleScreen.game;

/**
 * Class of static methods that return true for valid command line input
 * and return false if not
 * 
 * CPSC 224-01, Spring 2018
 * Final Project
 * 
 * @author Anthony Niehuser
 * 
 * @version v2.1 4/29/2018
 */
public class InputHandler {
	//parameters used in handler
	private static String prompt = "";
	private static String reg = "";
	private static StringContainer inString = new StringContainer();

	/**
	 * Test if input to keep particular dice in a roll is correct.
	 * @param value StringContainer to assign input to
	 * @return true if valid, false if not
	 * @deprecated
	 */
	public static boolean dieToKeep(StringContainer value){
		prompt = "Input y/n to keep or reroll a die";
		reg = "[ynYN]{" + game.getDieNum()+ "}";
		inString = value;
		return handler();
	}
	/**
	 * Test if input to play again is correct.
	 * @param value StringContainer to assign input to
	 * @return true if valid, false if not
	 * @deprecated
	 */
	public static boolean playAgain(StringContainer value){
		prompt = "Input y/n to keep playing or quit"; 
		reg = "[ynYN]";
		inString = value;
		return handler();
	}
	/**
	 * Test if input for updating config file is correct.
	 * @param value  StringContainer to assign input to 
	 * @return true if valid, false if not
	 * @deprecated
	 */
	public static boolean updateConfig(StringContainer value){
		prompt = "enter 'y' if you would like to change the configuration: ";
		reg = "[ynYN]";
		inString = value;
		return handler();
	}
	/**
	 * Test if input for updating sides is correct
	 * @param value  StringContainer to assign input to 
	 * @return true if valid, false if not
	 * @deprecated
	 */
	public static boolean newSides(StringContainer value){
		prompt = "enter the number of sides on each die; ";
		reg = "\\d+";
		inString = value;
		
		//first test valid regex
		if(!handler()){
			return false;
		}
		//second test for appropriate value
		int sides = Integer.parseInt(value.getString());
		if(sides < TitleScreen.game.MIN_SIDES){
			System.out.println("Must have at least " + TitleScreen.game.MIN_SIDES + " number of sides");
			return false;
		}
		return true;
	}
	/**
	 * Test if input for updating die is correct
	 * @param value  StringContainer to assign input to 
	 * @return true if valid, false if not
	 * @deprecated
	 */
	public static boolean newDie(StringContainer value){
		prompt = "enter the number of die in play: ";
		reg = "\\d+";
		inString = value;
		
		//first test valid regex
		if(!handler()){
			System.out.println("");
			return false;
		}
		//second test for appropriate value
		int die = Integer.parseInt(value.getString());
		if(die < TitleScreen.game.MIN_DIE){
			System.out.println("Must have at least " + TitleScreen.game.MIN_DIE + " number of die");
			return false;
		}
		return true;
	}
	/**
	 * Test if input for updating rolls is correct
	 * @param value  StringContainer to assign input to 
	 * @return true if valid, false if not
	 * @deprecated
	 */
	public static boolean newRolls(StringContainer value){
		prompt = "enter the number of rolls per hand: ";
		reg = "\\d+";
		inString = value;
		
		//first test for valid regex
		if(!handler()){
			return false;
		}
		//second test for appropriate value
		int rolls = Integer.parseInt(value.getString());
		if(rolls < TitleScreen.game.MIN_ROLLS){
			System.out.println("Must have at least " + TitleScreen.game.MIN_ROLLS + " rolls per hand");
			return false;
		}
		return true;
	}
	/**
	 * Determines if input is 1) a valid key and 2) if that key has already been 
	 * assigned a value
	 * @param value  StringContainer to assign input to
	 * @param scores Dictionary containing current score values
	 * @return true if valid, false if not
	 */
	public static boolean scoreToKeep(StringContainer value, Hashtable<String,Integer> scores){
//		System.out.println("Type Score you wish to keep: ");
//		value.setString(Main.in.nextLine());
		for(String k : scores.keySet()){
			if(k.equals(value.getString()) && scores.get(k) < 0){
				return true;
			}
		}
		return false;
	}
	/**
	 * Gets input from command prompt and tests if it matches
	 * current regex
	 * @return true if matches regex, false otherwise
	 */
	private static boolean handler(){
		//get input
		System.out.println(prompt);
		Scanner scanner = new Scanner(System.in);
		inString.setString(scanner.nextLine());
		
		//test regex
		if(!inString.getString().matches(reg)){
			System.out.println("Invalid input");
			return false;
		}
		return true;
	}
	
}
