package hw6;

import java.util.Hashtable;

/**
 * Class with static methods to determine attributes about a 
 * given roll in a hand
 * 
 * CPSC 224-01, Spring 2018
 * Programming Assignment #6
 * 
 * @author Anthony Niehuser
 * 
 * @version v1.1 3/19/2018
 */
public class Combo {
	/**
	 * Find the max number of recurring die roll value.
	 * @param rolls input hand
	 * @return int of max repeating value
	 */
	public static int maxOfAKindFound(int[] rolls){
		int max = 1;
		// get die counts for each number 
		Hashtable<Integer,Integer> counts = getDieCounts(rolls);
		
		// determine greatest count
		for( Integer k : counts.keySet()){
			int val = counts.get(k);
			if(max < val){
				max = val;
			}
		}
		return max;
	}
	/**
	 * Determine if hand is a full house or not.
	 * @param rolls input hand
	 * @return true if full house, false if not
	 */
	public static boolean fullHouseFound(int[] rolls){
		boolean found3 = false; // 3 of  a kind
		boolean found2 = false; // 2 of  a kind
		//get die counts for each number
		Hashtable<Integer,Integer> counts = getDieCounts(rolls);
		
		// iterate through hashtable 
		for(Integer k : counts.keySet()){
			if (counts.get(k) >= 5){
				found3 = true;
				found2 = true;
				break;
			} else if(!found3 && counts.get(k) >= 3){
				found3 = true;
			} else if (!found2 && counts.get(k) >= 2){
				found2 = true;
			} 
		}
		return found3 && found2;
	}
	
	/**
	 * Determine number of consecutive numbers in hand.
	 * @param rolls input hand sorted
	 * @return int value of largest straight
	 */
	public static int maxStraightFound(int [] rolls){ 
		int maxLength = 1;
		int currLength = 1;
		for(int i=0; i<rolls.length-1; i++){
			// compare current to next value
			if(rolls[i] + 1 == rolls[i+1]){
				currLength++;
			} else if (rolls[i] + 1 < rolls[i+1]){
				currLength = 1;
			}
			// increment maxlength if necessary
			if(currLength > maxLength){
				maxLength = currLength;
			}
		}
		return maxLength;
	}
	/**
	 * Creates a dictionary containing roll counts of each die side
	 * @param rolls input hand
	 * @return hashtable<Integer, Integer> with key being die side and value 
	 * of number of occurences
	 */
	private static Hashtable<Integer, Integer> getDieCounts(int[] rolls){
		Hashtable<Integer,Integer> counts = new Hashtable<Integer,Integer>();
		for(int i=0; i<rolls.length; i++){
			//if already contains key, increment value
			if(counts.containsKey(rolls[i])){
				counts.put(rolls[i], counts.get(rolls[i]) + 1);
			} else { // else add new key
				counts.put(rolls[i], 1);
			}
		}
		return counts;
	}

}
