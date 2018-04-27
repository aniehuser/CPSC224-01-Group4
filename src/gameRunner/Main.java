package gameRunner;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import factions.*;
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
	public static Random gen = new Random();
	public static boolean[] genKeep(){
		boolean[] keep = new boolean[7];
		for(int i=0; i<7; i++){
			keep[i] = gen.nextBoolean();
		}
		return keep;
	}
	public static void main(String[] args) {
		//Start Game
		game.start();
	
		//if problem starting game, ends program
		if(!game.isValidInstance()){
			System.out.println("Invalid Instance of Game object");
			return;
		}
		
		// intialize a player
		Player p = new Player(game.getDieSides(),
							   game.getDieNum(),
							   game.getRollsPerRound(), 
							   "Carl",
							   Faction.WHITE_WALKERS);
		
		boolean[] x = {true,true,true,true,true,true,false};
		p.rollInit();
		p.getHand().setDieByIndex(0,new Die(6 , true));
		p.getHand().setDieByIndex(1,new Die(1, true));
		p.getHand().setDieByIndex(2,new Die(1, false));
		p.getHand().setDieByIndex(3,new Die(1, false));
//		p.getScorer().setMultiplierByType(1, 1);
//		p.getScorer().setMultiplierByType(3, 2);
//		p.getScorer().setMultiplierByType(5, 1);
//		p.getScorer().setMultiplierByType(6, 1);
		System.out.println(p.getHand().toString());
		System.out.println(p.getFaction().isExecuted());
		System.out.println(p.getFaction().isSpecialHand());
		p.getFaction().executeSpecial();
		System.out.println(p.getFaction().isExecuted());
		System.out.println(p.getHand().toString());;
		p.rollOnce(x);
		System.out.println(p.getHand().toString());
		System.out.println(p.getMaxRolls());
		p.rollOnce(x);
		System.out.println(p.getHand().toString());
		System.out.println(p.getMaxRolls());
		p.rollOnce(x);
		System.out.println(p.getHand().toString());
		System.out.println(p.getMaxRolls());
		p.setScore("3");
		System.out.println(p.toString());
		p.getScorer().getMultiplierByType(1);
		
		if(true) return;

		// loop game until total rounds played == maxRounds (18). Do not increment round until
		// every player has completed their round. I may edit the Game object to make these checks
		// easier
		while(game.isGameOver()){
			
			// start a round by calling p.rollInit()
			p.rollInit();
			
			// get player's hand object by calling getHand()
			System.out.println(p.getHand().toString());
			
			//check if the round is over before rolling again.
			// NOTE:: This check occurs automatically inside of rollOnce(). Method just
			// returns without performing any functionality if constraints not met.
			if(!p.isRoundOver()){
				//After rollinit(), will use rollOnce() until the round is over. rollOnce()
				// checks if the user keeps all cards and automatically sets the round to be 
				// over. genKeep() generates a random boolean array which specifies which die to
				// keep. True to keep, false to reroll. indices of input directly correspond to indice
				// of die to keep
				p.rollOnce(genKeep());
				
				System.out.println(p.getHand().toString());
			}
			
			// perform another roll in the same fashion as before
			if(!p.isRoundOver()){
				p.rollOnce(genKeep());
				System.out.println(p.getHand().toString());
			}
			
			// this if statement should not execute due to the current state of the player object.
			// I put it in to show that these statements are handled by internal logic of player object
			if(!p.isRoundOver()){
				p.rollOnce(genKeep());
				System.out.println(p.getHand().toString());
			}
			
			// if round is over, then do end of round calculations. In actual game, this should be implemented
			// as a do while loop, until the user picks a score that they havent already chosen.
			if(p.isRoundOver()){
				// to print out players current score, call the player objects toString() method
				System.out.println(p.toString());
				
				// you can get the possible scores by calling getScorer. You can output this data using
				// the Score objects toString() method
				System.out.println(p.getScorer().toString());
	
				// have player indicate which score they want to keep. the keys are strings as follows:
				// {"1","2","3","4","5","6","7", "3 of a Kind", "4 of a Kind", "Full House", "The North",
				// "The South", "Easteros", "The Dead", "The Crown", "The Others", "Dragons", "Yahtzee" }
				String keepScore = "3 of a Kind";
				
				// first check to make sure the player hasnt already assigned the score to their scorecard.
				// this step is important because due to our special rules, i will not be doing checks in
				// setScore(key) to make sure that an already assigned score 
				if(p.isScoreSet(keepScore)){
					
					// set the player's score by inputting the string key of what the user chose
					p.setScore(keepScore);
				}
				
				// output updated score again using toString.
				System.out.println(p.toString());
			}
			
			// after a round is over, increment the Game object's round to track game state. 
			game.incrementRound();
		}
		
		// close globals
		in.close();
		game.end();
	}
}
