package factions;

public enum Faction {
	STARKS,
	LANNISTERS,
	TARGARYEN,
	WHITE_WALKERS,
	BARATHEON,
	GREYJOYS,
	CHILDREN_OF_THE_FOREST;


	@Override
	public String toString() {
		switch(this) {
			case STARKS: return "Starks";
			case LANNISTERS: return "Lannisters";
			case WHITE_WALKERS: return "White Walkers";
			case GREYJOYS: return "Greyjoys";
			case BARATHEON: return "Baratheon";
			case TARGARYEN: return "Taygaryen";
			case CHILDREN_OF_THE_FOREST: return "Children of the Forest";
			default: throw new IllegalArgumentException();
		}
	}
}