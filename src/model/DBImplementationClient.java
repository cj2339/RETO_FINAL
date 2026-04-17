package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Relational database mappings resolving Client logic interactions.
 * 
 * @author Santiago
 */
public class DBImplementationClient implements ClientDAO {
	private Connection con; 
	private PreparedStatement stmt;

	private ResourceBundle configFile;//the ResourceBundle object used to read configuration properties from a file, such as database connection details
	private String driverDB;//the driver class name for the database connection, which is read from the configuration file
	private String urlDB;//the URL of the database, which is read from the configuration file
	private String userDB;//the username for the database connection, which is read from the configuration file
	private String passwordDB;//the password for the database connection, which is read from the configuration file

	final String SQLSELECTALL = "SELECT * FROM client";//SQL query to select all clients from the database
	final String SQLDELETEBYCODE = "DELETE FROM client WHERE id_client = ?";//SQL query to delete a client from the database based on their unique identifier (code)
	final String SQLSELECTBYCODE = "SELECT * FROM client WHERE id_client = ?";//SQL query to select a client from the database based on their unique identifier (code)
	final String SQLUPDATEBYCODE = "UPDATE client SET name_client=?, surname_client=?, age_client=?, phone_client=?, email_client=? WHERE id_client=?";//SQL query to update a client's information in the database based on their unique identifier (code)
	final String SQLINSERT = "INSERT INTO client VALUES(?,?,?,?,?,?)";//SQL query to insert a new client into the database with the provided id, name, surname, age, phone and email
	final String SQLSELECTBOOKCLIENT = "SELECT * FROM book WHERE id_client=?";//SQL query to check if a client is associated with any booking in the database by their unique identifier (id)
	final String SQLPHONE = "SELECT * FROM client WHERE phone_number=?";
	final String SQLEMAIL = "SELECT * FROM client WHERE email=?";
	final String SQLPHONEEXCLUDE = "SELECT * FROM client WHERE phone_number!=? AND id_client!=?";
	final String SQLEMAILEXCLUDE = "SELECT * FROM client WHERE email!=? AND id_client!=?";
	
	public DBImplementationClient() {
		this.configFile = ResourceBundle.getBundle("configClass");
		this.driverDB = this.configFile.getString("Driver");
		this.urlDB = this.configFile.getString("Conn");
		this.userDB = this.configFile.getString("DBUser");
		this.passwordDB = this.configFile.getString("DBPass");
	}

	private void openConnection() {
		try {
			con = DriverManager.getConnection(urlDB, this.userDB, this.passwordDB);
		} catch (SQLException e) {
			System.out.println("Error to open BD");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves all clients from the database.
	 *
	 * @return List containing all clients currently in the database.
	 */
	@Override
	public List<Client> getAllClient() {//this method retrieves all clients from the database
		//by executing the SQLSELECTALL query and returns a list of Client objects representing the retrieved clients
		List<Client> clients = new ArrayList<>();
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTALL);
			java.sql.ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				clients.add(new Client(//creates a new Client object using the data retrieved from the database and adds it to the clients list
						rs.getString("id_client"),
						rs.getString("name_client"),
						rs.getString("surname_client"),
						rs.getInt("age_client"),
						rs.getInt("phone_client"),
						rs.getString("email_client")
						));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return clients;
	}

	/**
	 * Retrieves a specific client based on their client ID.
	 *
	 * @param client The client object with an ID attribute set to search for.
	 * @return The matching Client object or null if not found.
	 */
	@Override
	public Client getClientByCode(Client client) {
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTBYCODE);
			stmt.setString(1, client.getIdClient());
			java.sql.ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Client found = new Client(
						rs.getString("id_client"),
						rs.getString("name_client"),
						rs.getString("surname_client"),
						rs.getInt("age_client"),
						rs.getInt("phone_client"),
						rs.getString("email_client")
						);
				rs.close(); stmt.close(); con.close();
				return found;
			}
			rs.close(); stmt.close(); con.close();
		} catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
		return null;
	}

	/**
	 * Deletes a client based on their identification code.
	 *
	 * @param client The client holding the code.
	 * @return true if deleted properly, false otherwise.
	 */
	@Override
	public boolean deleteClient(Client client) {//this method deletes a client from the database
		//by executing the SQLDELETEBYCODE query with the client's unique identifier (code) and returns true if the deletion was successful, or false otherwise
		boolean ok = false;
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLDELETEBYCODE);
			stmt.setString(1, client.getIdClient());
			if (stmt.executeUpdate() > 0) ok = true;
			stmt.close(); con.close();
		} catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
		return ok;
	}

	/**
	 * Updates an existing client's details tracking their ID.
	 *
	 * @param client The matching Client with replaced attributes.
	 * @return true on success update, false alternatively.
	 */
	@Override
	public boolean updateClientByCode(Client client) {//this method updates a client's information in the database based on their unique identifier (code)
		boolean ok = false;
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLUPDATEBYCODE);
			stmt.setString(1, client.getNameClient());
			stmt.setString(2, client.getSurnameClient());
			stmt.setInt(3, client.getAgeClient());
			stmt.setInt(4, client.getPhoneClient());
			stmt.setString(5, client.getEmailClient());
			stmt.setString(6, client.getIdClient());
			if (stmt.executeUpdate() > 0) ok = true;//by executing the SQLUPDATEBYCODE query with the client's updated information and unique identifier (code)
			//and returns true if the update was successful, or false otherwise
			stmt.close(); con.close();
		} catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
		return ok;
	}

	/**
	 * Submits and processes a new Client persistence into the system layer.
	 *
	 * @param client A new client properties structure.
	 * @return true if insertion returns properly, false on collision.
	 */
	@Override
	public boolean insertClient(Client client) {//this method inserts a new client into the database by executing the SQLINSERT query with the client's id, name, surname, age, phone and email
		boolean ok = false;
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLINSERT);
			stmt.setString(1, client.getIdClient());
			stmt.setString(2, client.getNameClient());
			stmt.setString(3, client.getSurnameClient());
			stmt.setInt(4, client.getAgeClient());
			stmt.setInt(5, client.getPhoneClient());
			stmt.setString(6, client.getEmailClient());
			if (stmt.executeUpdate() > 0) ok = true;
			stmt.close(); con.close();
		} catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
		return ok;
	}

	/**
	 * Validates whether a given client ID handles active reservations under the book logs.
	 *
	 * @param id The ID to check against tracking system.
	 * @return true if it belongs to a book, false if transparent.
	 */
	@Override
	public boolean checkClientInBook(String id) {//this method checks if a client is associated with any booking in the database 
		//by executing the SQLSELECTBOOKCLIENT query with the client's unique identifier (id)
		boolean existe = false;
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTBOOKCLIENT);
			stmt.setString(1, id);
			java.sql.ResultSet rs = stmt.executeQuery();//and returns true if the client is associated with at least one booking, or false otherwise
			if (rs.next()) existe = true;
			rs.close(); stmt.close(); con.close();
		} catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
		return existe;
	}
	
	@Override
	public boolean phoneClientExists(String phone) {
		boolean exists=false;
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLPHONE);
			stmt.setString(1, phone);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) { 
				exists = true;
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return exists;
	}
	
	@Override
	public boolean emailClientExists(String email) {
		boolean exists=false;
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLEMAIL);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) { 
				exists = true;
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return exists;
	}
	
	@Override
	public boolean phoneClientExistsExclude(String phone, String id) {
		boolean exists=false;
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLPHONEEXCLUDE);
			stmt.setString(1, phone);
			stmt.setString(2, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) { 
				exists = true;
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return exists;
	}
	
	@Override
	public boolean emailClientExistsExclude(String email, String id) {
		boolean exists=false;
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLEMAILEXCLUDE);
			stmt.setString(1, email);
			stmt.setString(2, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) { 
				exists = true;
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return exists;
	}


}