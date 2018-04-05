package hw6;

import java.util.Hashtable;

public class Player {
	// non-int score card keys
	private final String[] lowerScoreTypes = {"Upper Total", "3 of a Kind","4 of a Kind","Full House",
			"Small Straight","Large Straight","Yahtzee","Chance", "Lower Total", "Grand Total"};
	private Hashtable<String,Integer> scores; // store all value of score card. -1 means unnassigned
	private int rounds; // current number of rounds
	private int sides; // sides of dice
	private int die; // number of die
	private int rolls; // number of rolls
	private int maxRounds; // max rounds in a game
	private static int player = 0; //static variable, increments with each new player
	
	/**
	 * Constructor
	 * @param sides sides of dice
	 * @param die number of die
	 * @param rolls rolls per round
	 */
	public Player(int sides, int die, int rolls){
		player++;
		rounds = 0;
		this.sides = sides;
		this.die = die;
		this. rolls = rolls;
		scores = initDict(); 
		maxRounds = scores.size() - 3;
	}
	/**
	 * Execute a single round for this player object
	 */
	public void doRound(){
		System.out.println("Player " + player);
		if(rounds == maxRounds){
			System.out.println("Error: Played too many rounds");
			return;
		}
		
		// Declare and Initialize varaibles 
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
		goodInput = InputHandler.scoreToKeep(inStr, scores);
		while(!goodInput){
			System.out.println("Invalid key or score has already been assigned, try again");
			goodInput = InputHandler.scoreToKeep(inStr, scores);
		}
		
		// set score and recalculate totals 
		scores.put(inStr.getString(), score.getScore(inStr.getString()));
		calculateTotals();
		
		//increment rounds
		rounds++;
	}
	/**
	 * Calculate the upper, lower and grand totals
	 */
	private void calculateTotals() {
		scores.put("Upper Total", sumUpper());
		scores.put("Lower Total", sumLower());
		scores.put("Grand Total", scores.get("Upper Total") + scores.get("Lower Total"));
	}
	/**
	 * Calculate total score of upper score card
	 * @return  int of sum of upper score card
	 */
	private int sumUpper(){
		int sum = 0;
		for(int i=1; i<=sides; i++){
			String k = Integer.toString(i);
			int v = scores.get(k);
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
			int v = scores.get(lowerScoreTypes[i]);
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
	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append("Current Score Card:\n");
		for(int i=1; i<=sides; i++){
			int v = scores.get(Integer.toString(i));
			b.append("Score " + ((v < 0) ? "nil" : v) + " on the " + i +" line.\n");
		}
		for(int i=0; i<lowerScoreTypes.length; i++) {
			int v = scores.get(lowerScoreTypes[i]);
			b.append("Score " + ((v < 0) ? "nil" : v) + " on the " + lowerScoreTypes[i] + " line.\n");
		}
		return b.toString();
	}
}
