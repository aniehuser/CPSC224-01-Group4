package gameRunner;

public class Die implements Cloneable, Comparable<Die>{
	private int type;
	private boolean special;
	//blau
	public Die() {
		type = 1;
		special = false;
	}
	public Die(Die other){
		type = other.type;
		special = other.special;
	}
	public Die(int type, boolean special){
		this.type = type;
		this.special = special;
	}
	public int getValue(){
		return (type) * (special ?  2 :  1 );
	}
	public void setType(int type, boolean special){
		this.type = type;
		this.special = special;
	}
	public void setSpecial(boolean special){
		this.special = special;
	}
	public int getType(){
		return type;
	}
	public boolean isSpecial(){
		return special;
	}
	public boolean greaterThan(Die other){
		return type > other.getType() || (type == other.type && (special && !other.special));
	}
	public boolean lessThan(Die other){
		return type < other.getType() || (type == other.type && (!special && other.special));
	}
	public boolean equalTo(Die other) {
		return type == other.getType() && special == other.special;
	}
	public boolean greaterThanEqualTo(Die other){
		return type >= other.getType();
	}
	public boolean lessThanEqualTo(Die other){
		return type <= other.getType();
	}
	public boolean notEqualTo(Die other){
		return type != other.getType();
	}
	
	@Override
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
	public int compareTo(Die other) {
		return (greaterThan(other)) ? 1 :
			   (equalTo(other))     ? 0 : -1;
	}
	
	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append("Type " + type);
		b.append("\nSpecial " + special);
		b.append("\nValue " + getValue() + "\n");
		return b.toString();
	}
}
