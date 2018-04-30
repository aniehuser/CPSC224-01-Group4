package gameRunner;

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
 * @version v1.2 4/29/2018
 */
public class Combo {
	/**
	 * Find the max number of recurring die roll value.
	 * @param rolls input hand
	 * @return int of max repeating value
	 */
	public static int maxOfAKindFound(Die[] rolls){
		int max = 1;
		// get die counts for each number 
		Hashtable<Integer,Integer> counts = getDieCounts(rolls);
		
		// determine greatest count
		for( int k : counts.keySet()){
			int val = counts.get(k);
			if(max < val){
				max = val;
			}
		}
		return max;
	}
	/**
	 * Returns the number of rare die by a given type.
	 * @param rolls	Array of Die class
	 * @param type the type for which to find the number of rare rolls
	 * @return integer of number of rare's of given type
	 */
	public static int numSpecialsByType(Die[] rolls, int type){
		Hashtable<Integer,Integer> specs = getSpecialDieCounts(rolls);
		return (specs.containsKey(type)) ? specs.get(type) : 0;
	}
	/**
	 * Determines if 5 dice of type 1 are in the hand.
	 * @param rolls the given hand
	 * @return true if 5 of type 1 are in the hand, false otherwise
	 */
	public static boolean northFound(Die[] rolls){
		return maxOfGivenKindFound(1, rolls) >= 5;
	}
	/**
	 * Determines if 5 dice of type 2 are in the hand.
	 * @param rolls the given hand
	 * @return true if 5 of type 2 are in the hand, false otherwise
	 */
	public static boolean southFound(Die[] rolls){
		return maxOfGivenKindFound(2, rolls) >= 5;
	}
	/**
	 * Determines if 5 dice of type 3 are in the hand.
	 * @param rolls the given hand
	 * @return true if 5 of type 3 are in the hand, false otherwise
	 */
	public static boolean easterosFound(Die[] rolls){
		return maxOfGivenKindFound(3, rolls) >= 5;
	}
	/**
	 * Determines if 5 dice of type 4 are in the hand.
	 * @param rolls the given hand
	 * @return true if 5 of type 4 are in the hand, false otherwise
	 */
	public static boolean deadFound(Die[] rolls){
		return maxOfGivenKindFound(4, rolls) >= 5;
	}
	/**
	 * Determines if 5 dice of type 5 are in the hand.
	 * @param rolls the given hand
	 * @return true if 5 of type 5 are in the hand, false otherwise
	 */
	public static boolean crownFound(Die[] rolls){
		return maxOfGivenKindFound(5, rolls) >= 5;
	}
	/**
	 * Determines if 5 dice of type 6 are in the hand.
	 * @param rolls the given hand
	 * @return true if 5 of type 6 are in the hand, false otherwise
	 */
	public static boolean othersFound(Die[] rolls){
		return maxOfGivenKindFound(6, rolls) >= 5;
	}
	/**
	 * Determines if 5 dice of type 7 are in the hand.
	 * @param rolls the given hand
	 * @return true if 5 of type 7 are in the hand, false otherwise
	 */
	public static boolean dragonsFound(Die[] rolls){
		return maxOfGivenKindFound(7, rolls) >= 5;
	}
	/**
	 * Determines number of a given type in the hand. 
	 * @param type the type of die to find in the hand.
	 * @param rolls rolls of the given hand.
	 * @return int of number of type in hand. 
	 */
	public static int maxOfGivenKindFound(int type, Die[] rolls){
		Hashtable<Integer,Integer> counts = getDieCounts(rolls);
		return counts.containsKey(type) ? counts.get(type) : 0;
	}
	/**
	 * Determine if hand is a full house or not.
	 * @param rolls input hand
	 * @return true if full house, false if not
	 */
	public static boolean fullHouseFound(Die[] rolls){
		boolean first3 = false; // 3 of  a kind
		boolean second3 = false; // 2 of  a kind
		//get die counts for each number
		Hashtable<Integer,Integer> counts = getDieCounts(rolls);
		
		// iterate through hashtable 
		for(Integer k : counts.keySet()){
			if (counts.get(k) >= 6){
				first3 = true;
				second3 = true;
				break;
			} else if(!first3 && counts.get(k) >= 3){
				first3 = true;
			} else if (!second3 && counts.get(k) >= 3){
				second3 = true;
			} 
		}
		return first3 && second3;
	}
	
	/**
	 * NOTE:: THIS METHOD IS IRRELEVANT :::        
	 * Determine number of consecutive numbers in hand.
	 * @param rolls input hand sorted
	 * @return int value of largest straight
	 * @deprecated
	 */
	public static int maxStraightFound(Die[] rolls){ 
		int maxLength = 1;
//		int currLength = 1;
//		for(int i=0; i<rolls.length-1; i++){
//			// compare current to next value
//			if(rolls[i] + 1 == rolls[i+1]){
//				currLength++;
//			} else if (rolls[i] + 1 < rolls[i+1]){
//				currLength = 1;
//			}
//			// increment maxlength if necessary
//			if(currLength > maxLength){
//				maxLength = currLength;
//			}
//		}
		return maxLength;
	}
	/**
	 * Creates a dictionary containing roll counts of each die side
	 * @param rolls input hand
	 * @return hashtable<Integer, Integer> with key being die side and value 
	 * of number of occurences
	 */
	private static Hashtable<Integer, Integer> getDieCounts(Die[] rolls){
		Hashtable<Integer,Integer> counts = new Hashtable<Integer,Integer>();
		for(int i=0; i<rolls.length; i++){
			//if already contains key, increment value
			if(counts.containsKey(rolls[i].getType())){
				counts.put(rolls[i].getType(), counts.get(rolls[i].getType()) + 1);
			} else { // else add new key
				counts.put(rolls[i].getType(), 1);
			}
		}
		return counts;
	}
	/**
	 * Find number of all rare dice by type
	 * @param rolls the given hand
	 * @return A Hashtable with the number of rare rolls by type
	 */
	private static Hashtable<Integer, Integer> getSpecialDieCounts(Die[] rolls){
		Hashtable<Integer,Integer> counts = new Hashtable<Integer,Integer>();
		for(int i=0; i<rolls.length; i++){
			//if already contains key, increment value
			if(rolls[i].isSpecial()){
				if(counts.containsKey(rolls[i].getType())){
					counts.put(rolls[i].getType(), counts.get(rolls[i].getType()) + 1);
				} else { // else add new key
					counts.put(rolls[i].getType(), 1);
				}
			}
		}
		return counts;
	}
	

}
