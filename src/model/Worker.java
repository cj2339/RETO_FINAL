package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Worker {
	private String idWorker;// Unique identifier for the worker
	private TypeWorker service;// Type of service provided by the worker 
	private String name;// First name of the worker
	private String surname;// Last name of the worker
	private Date hiringDate;// Date when the worker was hired
	private String phoneNumber;// Contact phone number for the worker
	private String email;// Contact email address for the worker
	private int age;// Age of the worker
	private boolean spanish; //The worker can speak spanish
	private boolean english; //the worker can speak english
	private Cruise cruise; 
	
	public Worker() {
		super();
	}

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
