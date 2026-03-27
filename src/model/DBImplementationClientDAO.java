package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DBImplementationClientDAO implements ClientDAO {
	private Connection connection;
	private PreparedStatement statement;
	
	private ResourceBundle configFile;
	private String driverDB;
	private String urlDB;
	private String userDB;
	private String passwordDB;
	
	final String SQLSELECTALL = "SELECT * FROM client";
	final String SQLDELETEBYCODE = "DELETE FROM client WHERE id_client = ?";
	final String SQLSELECTBYCODE = "SELECT * FROM client WHERE id_client=?";
	final String SQLUPDATEBYCODE = "UPDATE client set type_cruise=?,name_cruise=?, num_rooms=?,capacity_max=? WHERE cod_cruise=?";
	final String SQLINSERT = "INSERT INTO CLIENT VALUES(?,?,?,?,?);";
	final String SQLSELECTBOOKBYCREUISE = "SELECT * FROM BOOK WHERE id_client=?";
	
	public DBImplementationClientDAO() {
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
		// TODO Auto-generated method stub
		List<Client> clients = new ArrayList<>();
		Client client;
		
		
		return clients;
	}

	@Override
	public Client getClientByCode(Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteClient(Client client) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateClientByCode(Client client) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertClient(Client client) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	
	

}
