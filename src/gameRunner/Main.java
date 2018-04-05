package gameRunner;

import java.util.Scanner;
//import hw5.Hand;

/**
 * This program performs basic rolling and score calculating in
 * the board game, Yahtzee. It is intended to emulate a c++
 * version of the game provided by the instructor.
 * 
 * CPSC 224-01, Spring 2018
 * Programming Assignment #6
 * 
 * @author Anthony Niehuser
 * 
 * @version v1.1 3/19/2018
 */
public class Main {
	public static final Scanner in = new Scanner(System.in); //terminal input object
	public static final Game game = new Game(); 

	/**
	 * main method, provides insertion point for program
	 * @param args  terminal input
	 */
	public static void main(String[] args) {
		boolean goodInput = false;
		StringContainer inStr = new StringContainer();
		Player p1;
		
		//Start Game
		game.start();
		
		//if problem starting game, ends program
		if(!game.isValidInstance()){
			System.out.println("Invalid Instance of Game object");
			return;
		}
		
		// Update config file if specified by user
		System.out.println(game.toString());
		while(!goodInput){
			goodInput = InputHandler.updateConfig(inStr);
		}
		if(inStr.getString().toLowerCase().equals("y")){
			game.update();
		}
		
		// Create Player
		p1 = new Player(game.getDieSides(), game.getDieNum(), game.getRollsPerRound());
		
		// Game loop
		while(game.getCurrentRound()<game.getMaxRounds()){
			p1.doRound();
			game.incrementRound();
		}
		
		// print final score
		System.out.println(p1.toString());
		
		// close globals
		in.close();
		game.end();
	}
}
