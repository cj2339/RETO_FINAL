package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Worker {
	private String idWorker;
	private TypeWorker service;
	private String name;
	private String surname;
	private Date hiringDate;
	private String phoneNumber;
	private String email;
	private int age;
	private boolean spanish; 
	private boolean english; 
	private Cruise cruise;

	public Worker() {
		super();
	}

	/**
	 * Constructor for the Worker class that initializes all attributes of a worker,
	 * including their unique identifier, type of service, name, surname, hiring
	 * date, contact information, age, language skills, and associated cruise. This
	 * constructor allows for the creation of a Worker object with all relevant
	 * details provided at the time of instantiation.
	 * 
	 * @param idWorker
	 * @param service
	 * @param name
	 * @param surname
	 * @param hiringDate
	 * @param phoneNumber
	 * @param email
	 * @param age
	 * @param spanish
	 * @param english
	 * @param cruise
	 */
	public Worker(String idWorker, TypeWorker service, String name, String surname, Date hiringDate, String phoneNumber,
			String email, int age, boolean spanish, boolean english, Cruise cruise) {
		super();
		this.idWorker = idWorker;
		this.service = service;
		this.name = name;
		this.surname = surname;
		this.hiringDate = hiringDate;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.age = age;
		this.spanish = spanish;
		this.english = english;
		this.cruise = cruise;
	}

	public String getIdWorker() {
		return idWorker;
	}

	public void setIdWorker(String idWorker) {
		this.idWorker = idWorker;
	}

	public TypeWorker getService() {
		return service;
	}

	public void setService(TypeWorker service) {
		this.service = service;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(Date hiringDate) {
		this.hiringDate = hiringDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isSpanish() {
		return spanish;
	}

	public void setSpanish(boolean spanish) {
		this.spanish = spanish;
	}

	public boolean isEnglish() {
		return english;
	}

	public void setEnglish(boolean english) {
		this.english = english;
	}

	public Cruise getCruise() {
		return cruise;
	}

	public void setCruise(Cruise cruise) {
		this.cruise = cruise;
	}

}
