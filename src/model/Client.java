package model;

public class Client {

	private String idClient;
	private String nameClient;
	private String surnameClient;
	private int ageClient;

	/**
	 * Constructor that initializes the client's information with the provided id,
	 * name, surname, and age.
	 * 
	 * @param idClient
	 * @param nameClient
	 * @param surnameClient
	 * @param ageClient
	 */
	public Client(String idClient, String nameClient, String surnameClient, int ageClient) {
		this.idClient = idClient;
		this.nameClient = nameClient;
		this.surnameClient = surnameClient;
		this.ageClient = ageClient;

	}

	public String getIdClient() {
		return idClient;
	}

	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}

	public String getNameClient() {
		return nameClient;
	}

	public void setNameClient(String nameClient) {
		this.nameClient = nameClient;
	}

	public String getSurnameClient() {
		return surnameClient;
	}

	public void setSurnameClient(String surnameClient) {
		this.surnameClient = surnameClient;
	}

	public int getAgeClient() {
		return ageClient;
	}

	public void setAgeClient(int ageClient) {
		this.ageClient = ageClient;
	}

}
