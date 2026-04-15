package model;

/**
 * Represents an administrator entity in the system handling login and credentials.
 */
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
	 * @param name     The administrator's username.
	 * @param password The administrator's password.
	 */
	public Administrator(String name, String password) {
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