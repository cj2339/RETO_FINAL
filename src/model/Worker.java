package model;

import java.util.Date;

public class Worker {
	private String idWorker;
	private TypeWorker service;
	private String name;
	private String surname;
	private Date hiringDate;
	private String phoneNumber;
	private String email;
	private int age;
	private Language language;
	private int codCruise;
	
	public Worker() {
		super();
	}

	public Worker(String idWorker, TypeWorker service, String name, String surname, Date hiringDate,
			String phoneNumber, String email, int age, Language language, int codCruise) {
		super();
		this.idWorker = idWorker;
		this.service = service;
		this.name = name;
		this.surname = surname;
		this.hiringDate = hiringDate;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.age = age;
		this.language = language;
		this.codCruise = codCruise;
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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public int getCodCruise() {
		return codCruise;
	}

	public void setCodCruise(int codCruise) {
		this.codCruise = codCruise;
	}
	
}
