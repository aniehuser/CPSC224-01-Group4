package gameRunner;

/**
 * Container class to pass Strings by reference.
 * 
 * CPSC 224-01, Spring 2018
 * Programming Assignment #6
 * 
 * @author Anthony Niehuser
 * 
 * @version v1.0 3/05/2018
 */
public class StringContainer {
	private String value;
	
	/**
	 * Default Constructor
	 */
	public StringContainer(){
		value = "";
	}
	/**
	 * Constructor to specify value
	 * @param value String value
	 */
	public StringContainer(String value){
		this.value = value;
	}
	/**
	 * Getter for string value
	 * @return value
	 */
	public String getString(){
		return value;
	}
	/**
	 * Setter for String value
	 * @param value to assign
	 */
	public void setString(String value){
		this.value = value;
	}
}
