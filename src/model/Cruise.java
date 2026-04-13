package model;

public class Cruise {

	private int codCruise;//the unique code that identifies the cruise
	private TypeCruise typeCruise;//the type of cruise, which is an instance of the TypeCruise class
	private String nameCruise;//the name of the cruise
	private int numRooms;//the number of rooms available on the cruise
	private int capacityMax;//the maximum capacity of passengers that the cruise can accommodate

	public Cruise() {
		this.codCruise = 0;
		this.typeCruise = null;
		this.nameCruise = null;
		this.numRooms = 0;
		this.capacityMax = 0;
	}

	public Cruise(int codCruise, TypeCruise typeCruise, String nameCruise, int numRooms, int capacityMax) {//constructor that initializes the cruise's information
		this.codCruise = codCruise;
		this.typeCruise = typeCruise;
		this.nameCruise = nameCruise;
		this.numRooms = numRooms;
		this.capacityMax = capacityMax;
	}

	public int getCodCruise() {
		return codCruise;
	}

	public void setCodCruise(int codCruise) {
		this.codCruise = codCruise;
	}

	public TypeCruise getTypeCruise() {
		return typeCruise;
	}

	public void setTypeCruise(TypeCruise typeCruise) {
		this.typeCruise = typeCruise;
	}

	public String getNameCruise() {
		return nameCruise;
	}

	public void setNameCruise(String nameCruise) {
		this.nameCruise = nameCruise;
	}

	public int getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	public int getCapacityMax() {
		return capacityMax;
	}

	public void setCapacityMax(int capacityMax) {
		this.capacityMax = capacityMax;
	}

}
