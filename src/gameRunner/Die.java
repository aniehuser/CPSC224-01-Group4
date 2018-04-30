package gameRunner;

/**
 * Represents an individual Die to be rolled.
 * 
 * CPSC 224-01, Spring 2018
 * Final Project
 * 
 * @author Anthony Niehuser
 *
 * @version v1.0  4/29/2018
 */
public class Die implements Cloneable, Comparable<Die>{
	private int type;
	private boolean special;
	private int value;
	
	/**
	 * Default Constructor
	 */
	public Die() {
		type = 1;
		special = false;
		value = 1;
	}
	/**
	 * Copy Constructor
	 * @param other
	 */
	public Die(Die other){
		type = other.type;
		special = other.special;
		value  = other.value;
	}
	/**
	 * Constructor to specify other type and rareness
	 * @param type
	 * @param special
	 */
	public Die(int type, boolean special){
		this.type = type;
		this.special = special;
		this.value = type;
	}
	/**
	 * Get the number of point this die is worth
	 * @return int value of points
	 */
	public int getValue(){
		return value * (special ?  2 :  1 );
	}
	/**
	 * Setter for value.
	 * @param value int to be new value.
	 */
	public void setValue(int value){
		this.value = value;
	}
	/**
	 * Setter for type. 
	 * @param type int to be new type.
	 * @param special true if to be rare, false if not
	 */
	public void setType(int type, boolean special){
		this.type = type;
		this.special = special;
	}
	/**
	 * Setter for special.
	 * @param special true if to be rare, false if not
	 */
	public void setSpecial(boolean special){
		this.special = special;
	}
	/**
	 * Getter for type
	 * @return type of die
	 */
	public int getType(){
		return type;
	}
	/**
	 * Getter for special
	 * @return true if rare, false if not
	 */
	public boolean isSpecial(){
		return special;
	}
	/**
	 * Compare two Die
	 * @param other die to compare
	 * @return true if this is greater, false otherwise
	 */
	public boolean greaterThan(Die other){
		return type > other.getType() || (type == other.type && (special && !other.special));
	}
	/**
	 * Compare two Die
	 * @param other die to compare
	 * @return true if this is less, false otherwise
	 */
	public boolean lessThan(Die other){
		return type < other.getType() || (type == other.type && (!special && other.special));
	}
	/**
	 * Compare two Die
	 * @param other die to compare
	 * @return true if this is equal to other, false otherwise
	 */
	public boolean equalTo(Die other) {
		return type == other.getType() && special == other.special;
	}
	/**
	 * Compare two Die
	 * @param other die to compare
	 * @return true if this is greater or queal to, false otherwise
	 */
	public boolean greaterThanEqualTo(Die other){
		return type >= other.getType();
	}
	/**
	 * Compare two Die
	 * @param other die to compare
	 * @return true if this is greater, false otherwise
	 */
	public boolean lessThanEqualTo(Die other){
		return type <= other.getType();
	}
	/**
	 * Compare two Die
	 * @param other die to compare
	 * @return true if this is not equal to other, false otherwise
	 */
	public boolean notEqualTo(Die other){
		return type != other.getType();
	}

	@Override
	/** 
	 * Create a deep copy of this.
	 * @return deep copy of this
	 */
	public Die clone(){
		Die ret;
		try {
			ret = (Die) super.clone();
		} catch (CloneNotSupportedException e) {
			ret = null;
			e.printStackTrace();
		}
		return ret;
	}
	
	
	@Override
	/**
	 * Used to sort in Arrays.sort
	 * @return 1 if this greater than other,0 if equal, -1 otherwise
	 */
	public int compareTo(Die other) {
		return (greaterThan(other)) ? 1 :
			   (equalTo(other))     ? 0 : -1;
	}
	
	/**
	 * Return string value of die
	 */
	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append("Type " + type);
		b.append("\nSpecial " + special);
		b.append("\nValue " + getValue() + "\n");
		return b.toString();
	}
}
