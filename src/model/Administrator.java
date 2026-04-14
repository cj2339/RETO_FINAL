package model;

public class Administrator {
	private String name;
	private String password;

	/**
	 * Default constructor that initializes the administrator's information to null.
	 */
	public Administrator() {
		this.name = null;
		this.password = null;
	}

	/**
	 * Constructor that initializes the administrator's information with the
	 * provided name and password.
	 * 
	 * @param name
	 * @param password
	 */
	public Administrator(String name, String password) {// constructor that initializes the administrator's information
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}