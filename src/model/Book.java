package model;

import java.util.Date;

/**
 * Represents a booking entity containing details about the client, cruise, dates, and prices.
 * 
 * @author Iker
 */
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

	/**
	 * Default constructor initializing empty or default values for the booking.
	 */
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

	/**
	 * Constructor initializing a booking with all necessary fields.
	 *
	 * @param idClient       The client's identification string.
	 * @param codCruise      The cruise code.
	 * @param originCity     The city of origin.
	 * @param destinationCity The destination city.
	 * @param startDate      The start date of the booking.
	 * @param endDate        The end date of the booking.
	 * @param basePrice      The base calculated price.
	 * @param finalPrice     The final price after any modifications.
	 * @param roomNumber     The assigned room number.
	 */
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