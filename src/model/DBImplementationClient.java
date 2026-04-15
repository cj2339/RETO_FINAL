package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DBImplementationClient implements ClientDAO {
	private Connection connection;
	private PreparedStatement statement;
	
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
	
	public DBImplementationClient() {
		this.configFile = ResourceBundle.getBundle("configClass");
		this.driverDB = this.configFile.getString("Driver");
		this.urlDB = this.configFile.getString("Conn");
		this.userDB = this.configFile.getString("DBUser");
		this.passwordDB = this.configFile.getString("DBPass");
	}
	
	private void openConnection() {
		try {
			connection = DriverManager.getConnection(urlDB, this.userDB, this.passwordDB);
		} catch (SQLException e) {
			System.out.println("Error to open BD");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Client> getAllClient() {//this method retrieves all clients from the database
		//by executing the SQLSELECTALL query and returns a list of Client objects representing the retrieved clients
		List<Client> clients = new ArrayList<>();
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTALL);
			java.sql.ResultSet rs = statement.executeQuery();
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
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return clients;
	}

	@Override
	public Client getClientByCode(Client client) {
	    this.openConnection();
	    try {
	        statement = connection.prepareStatement(SQLSELECTBYCODE);
	        statement.setString(1, client.getIdClient());
	        java.sql.ResultSet rs = statement.executeQuery();
	        if (rs.next()) {
	            Client found = new Client(
	                rs.getString("id_client"),
	                rs.getString("name_client"),
	                rs.getString("surname_client"),
	                rs.getInt("age_client"),
	                rs.getInt("phone_client"),
	                rs.getString("email_client")
	            );
                rs.close(); statement.close(); connection.close();
                return found;
	        }
	        rs.close(); statement.close(); connection.close();
	    } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
	    return null;
	}

	@Override
	public boolean deleteClient(Client client) {//this method deletes a client from the database
		//by executing the SQLDELETEBYCODE query with the client's unique identifier (code) and returns true if the deletion was successful, or false otherwise
	    boolean ok = false;
	    this.openConnection();
	    try {
	        statement = connection.prepareStatement(SQLDELETEBYCODE);
	        statement.setString(1, client.getIdClient());
	        if (statement.executeUpdate() > 0) ok = true;
	        statement.close(); connection.close();
	    } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
	    return ok;
	}

	@Override
	public boolean updateClientByCode(Client client) {//this method updates a client's information in the database based on their unique identifier (code)
	    boolean ok = false;
	    this.openConnection();
	    try {
	        statement = connection.prepareStatement(SQLUPDATEBYCODE);
	        statement.setString(1, client.getNameClient());
	        statement.setString(2, client.getSurnameClient());
	        statement.setInt(3, client.getAgeClient());
	        statement.setInt(4, client.getPhoneClient());
	        statement.setString(5, client.getEmailClient());
	        statement.setString(6, client.getIdClient());
	        if (statement.executeUpdate() > 0) ok = true;//by executing the SQLUPDATEBYCODE query with the client's updated information and unique identifier (code)
	        //and returns true if the update was successful, or false otherwise
	        statement.close(); connection.close();
	    } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
	    return ok;
	}

	@Override
	public boolean insertClient(Client client) {//this method inserts a new client into the database by executing the SQLINSERT query with the client's id, name, surname, age, phone and email
	    boolean ok = false;
	    this.openConnection();
	    try {
	        statement = connection.prepareStatement(SQLINSERT);
	        statement.setString(1, client.getIdClient());
	        statement.setString(2, client.getNameClient());
	        statement.setString(3, client.getSurnameClient());
	        statement.setInt(4, client.getAgeClient());
	        statement.setInt(5, client.getPhoneClient());
	        statement.setString(6, client.getEmailClient());
	        if (statement.executeUpdate() > 0) ok = true;
	        statement.close(); connection.close();
	    } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
	    return ok;
	}

	@Override
	public boolean checkClientInBook(String id) {//this method checks if a client is associated with any booking in the database 
		//by executing the SQLSELECTBOOKCLIENT query with the client's unique identifier (id)
	    boolean existe = false;
	    this.openConnection();
	    try {
	        statement = connection.prepareStatement(SQLSELECTBOOKCLIENT);
	        statement.setString(1, id);
	        java.sql.ResultSet rs = statement.executeQuery();//and returns true if the client is associated with at least one booking, or false otherwise
	        if (rs.next()) existe = true;
	        rs.close(); statement.close(); connection.close();
	    } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
	    return existe;
	}
}