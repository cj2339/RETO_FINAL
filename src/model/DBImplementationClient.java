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

	private ResourceBundle configFile;
	private String driverDB;
	private String urlDB;
	private String userDB;
	private String passwordDB;

	final String SQLSELECTALL = "SELECT * FROM client";
	final String SQLDELETEBYCODE = "DELETE FROM client WHERE id_client = ?";
	final String SQLSELECTBYCODE = "SELECT * FROM client WHERE id_client = ?";
	final String SQLUPDATEBYCODE = "UPDATE client SET name_client=?, surname_client=?, age_client=? WHERE id_client=?";
	final String SQLINSERT = "INSERT INTO client VALUES(?,?,?,?)";
	final String SQLSELECTBOOKCLIENT = "SELECT * FROM book WHERE id_client=?";

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
	/**
	 * This method retrieves all clients from the database by executing the
	 * SQLSELECTALL query and returns a list of Client objects representing the
	 * retrieved clients. It opens a connection to the database, prepares and
	 * executes the SQL query, and iterates through the result set to create Client
	 * objects for each retrieved client. Finally, it closes all resources and
	 * returns the list of clients.
	 */
	public List<Client> getAllClient() {
		List<Client> clients = new ArrayList<>();
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTALL);
			java.sql.ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				clients.add(new Client(rs.getString("id_client"), rs.getString("name_client"),
						rs.getString("surname_client"), rs.getInt("age_client")));
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
	/**
	 * This method retrieves a client from the database based on their unique
	 * identifier (code) by executing the SQLSELECTBYCODE query. It opens a
	 * connection to the database, prepares and executes the SQL query with the
	 * client's code as a parameter, and checks if a matching record is found in the
	 * result set. If a matching record is found, it creates and returns a Client
	 * object representing the retrieved client. If no matching record is found, it
	 * returns null. Finally, it closes all resources used during the process.
	 */
	public Client getClientByCode(Client client) {
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTBYCODE);
			statement.setString(1, client.getIdClient());
			java.sql.ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return new Client(rs.getString("id_client"), rs.getString("name_client"),
						rs.getString("surname_client"), rs.getInt("age_client"));
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return null;
	}

	/**
	 * This method deletes a client from the database based on their unique
	 * identifier
	 */
	@Override
	public boolean deleteClient(Client client) {
		boolean ok = false;
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLDELETEBYCODE);
			statement.setString(1, client.getIdClient());
			if (statement.executeUpdate() > 0)
				ok = true;
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return ok;
	}

	/**
	 * This method updates the information of a client in the database based on
	 * their unique identifier (code) by executing the SQLUPDATEBYCODE query. It
	 * opens a connection to the database, prepares and executes the SQL query with
	 * the client's updated information as parameters, and checks if the update was
	 * successful by verifying if any records were affected. If the update was
	 * successful, it returns true; otherwise, it returns false. Finally, it closes
	 * all resources used during the process.
	 */
	@Override
	public boolean updateClientByCode(Client client) {
		boolean ok = false;
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLUPDATEBYCODE);
			statement.setString(1, client.getNameClient());
			statement.setString(2, client.getSurnameClient());
			statement.setInt(3, client.getAgeClient());
			statement.setString(4, client.getIdClient());
			if (statement.executeUpdate() > 0)
				ok = true;
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return ok;
	}

	@Override
	/**
	 * This method inserts a new client into the database by executing the SQLINSERT
	 */
	public boolean insertClient(Client client) {
		boolean ok = false;
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLINSERT);
			statement.setString(1, client.getIdClient());
			statement.setString(2, client.getNameClient());
			statement.setString(3, client.getSurnameClient());
			statement.setInt(4, client.getAgeClient());
			if (statement.executeUpdate() > 0)
				ok = true;
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return ok;
	}

	/**
	 * This method checks if a client with the given identifier (id) is associated
	 * with at least one booking in the database. It opens a connection to the
	 * database, prepares and executes the SQLSELECTBOOKCLIENT query with the
	 * client's id as a parameter, and checks if any records are returned in the
	 * result set. If at least one record is found, it returns true; otherwise, it
	 * returns false. Finally, it closes all resources used during the process.
	 */
	@Override
	public boolean checkClientInBook(String id) {
		boolean existe = false;
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTBOOKCLIENT);
			statement.setString(1, id);
			java.sql.ResultSet rs = statement.executeQuery();
			if (rs.next())
				existe = true;
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return existe;
	}

}
