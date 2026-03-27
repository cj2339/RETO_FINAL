package model;

public class Worker {
	private String idWorker;
	private TypeWorker service;
	private String name;
	private String surname;
	private String codCruise;
	
	public Worker(String idWorker, TypeWorker service, String name, String surname, String codCruise) {
		super();
		this.idWorker = idWorker;
		this.service = service;
		this.name = name;
		this.surname = surname;
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

	public String getCodCruise() {
		return codCruise;
	}

	public void setCodCruise(String codCruise) {
		this.codCruise = codCruise;
	}
	
}
