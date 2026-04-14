package model;

import java.util.Date;

public class Book {

	private String idClient;
	private int codCruise;
	private String originCity;
	private String destinationCity;
	private Date startDate;
	private Date endDate;
	private double basePrice;
	private double finalPrice;
	private int roomNumber;

	public Book() {
		this.idClient = null;
		this.codCruise = 0;
		this.originCity = null;
		this.destinationCity = null;
		this.startDate = null;
		this.endDate = null;
		this.basePrice = 0;
		this.finalPrice = 0;
		this.roomNumber = 0;
	}

	public Book(String idClient, int codCruise, String originCity, String destinationCity,
			Date startDate, Date endDate, double basePrice, double finalPrice, int roomNumber) {
		this.idClient = idClient;
		this.codCruise = codCruise;
		this.originCity = originCity;
		this.destinationCity = destinationCity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.basePrice = basePrice;
		this.finalPrice = finalPrice;
		this.roomNumber = roomNumber;
	}

	public String getIdClient() {
		return idClient;
	}
	public int getCodCruise() { 
		return codCruise; 
	}
	public String getOriginCity() {
		return originCity;
	}
	public String getDestinationCity() {
		return destinationCity; 
	}
	public Date getStartDate() {
		return startDate; 
	}
	public Date getEndDate() { 
		return endDate; 
	}
	public double getBasePrice() { 
		return basePrice; 
	}
	public double getFinalPrice() { 
		return finalPrice; 
	}
	public int getRoomNumber() {
		return roomNumber; 
	}
	public void setIdClient(String idClient) {
		this.idClient = idClient; 
	}
	public void setCodCruise(int codCruise) {
		this.codCruise = codCruise; 
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity; 
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity; 
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate; 
	}
	public void setEndDate(Date endDate) { 
		this.endDate = endDate; 
	}
	public void setBasePrice(double basePrice) { 
		this.basePrice = basePrice; 
	}
	public void setFinalPrice(double finalPrice) { 
		this.finalPrice = finalPrice; 
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber; 
	}
}