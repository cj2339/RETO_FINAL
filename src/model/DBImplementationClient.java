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
	public List<Client> getAllClient() {
		List<Client> clients = new ArrayList<>();
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTALL);
			java.sql.ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				clients.add(new Client(
						rs.getString("id_client"),
						rs.getString("name_client"),
						rs.getString("surname_client"),
						rs.getInt("age_client")
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
	            return new Client(
	                rs.getString("id_client"),
	                rs.getString("name_client"),
	                rs.getString("surname_client"),
	                rs.getInt("age_client")
	            );
	        }
	        rs.close(); statement.close(); connection.close();
	    } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
	    return null;
	}

	@Override
	public boolean deleteClient(Client client) {
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
	public boolean updateClientByCode(Client client) {
	    boolean ok = false;
	    this.openConnection();
	    try {
	        statement = connection.prepareStatement(SQLUPDATEBYCODE);
	        statement.setString(1, client.getNameClient());
	        statement.setString(2, client.getSurnameClient());
	        statement.setInt(3, client.getAgeClient());
	        statement.setString(4, client.getIdClient());
	        if (statement.executeUpdate() > 0) ok = true;
	        statement.close(); connection.close();
	    } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
	    return ok;
	}

	@Override
	public boolean insertClient(Client client) {
	    boolean ok = false;
	    this.openConnection();
	    try {
	        statement = connection.prepareStatement(SQLINSERT);
	        statement.setString(1, client.getIdClient());
	        statement.setString(2, client.getNameClient());
	        statement.setString(3, client.getSurnameClient());
	        statement.setInt(4, client.getAgeClient());
	        if (statement.executeUpdate() > 0) ok = true;
	        statement.close(); connection.close();
	    } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
	    return ok;
	}

	@Override
	public boolean checkClientInBook(String id) {
	    boolean existe = false;
	    this.openConnection();
	    try {
	        statement = connection.prepareStatement(SQLSELECTBOOKCLIENT);
	        statement.setString(1, id);
	        java.sql.ResultSet rs = statement.executeQuery();
	        if (rs.next()) existe = true;
	        rs.close(); statement.close(); connection.close();
	    } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
	    return existe;
	}
	
	
	
	
	
	

}
