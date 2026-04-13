package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DBImplementationCruise implements CruiseDAO {
	private Connection connection;
	private PreparedStatement statement;

	private ResourceBundle configFile;
	private String driverDB;
	private String urlDB;
	private String userDB;
	private String passwordDB;

	final String SQLSELECTALL = "SELECT * FROM cruise";//SQL query to select all cruises from the database
	final String SQLDELETEBYCODE = "DELETE FROM cruise WHERE cod_cruise = ?";//SQL query to delete a cruise from the database based on its unique identifier (code)
	final String SQLSELECTBYCODE = "SELECT * FROM cruise WHERE cod_cruise=?";//SQL query to select a cruise from the database based on its unique identifier (code)
	final String SQLUPDATEBYCODE = "UPDATE cruise set type_cruise=?,name_cruise=?, num_rooms=?,capacity_max=? WHERE cod_cruise=?";//SQL query to update a cruise's information in the database based on its unique identifier (code)
	final String SQLINSERT = "INSERT INTO cruise(name_cruise, type_cruise, num_rooms, capacity_max) VALUES(?,?,?,?);";//SQL query to insert a new cruise into the database with the provided name, type, number of rooms, and maximum capacity
	final String SQLSELECTWORKERBYCRUISE = "SELECT * FROM WORKER WHERE cod_cruise=?";//SQL query to check if a cruise is associated with any worker in the database by its unique identifier (code)
	final String SQLSELECTBOOKBYCREUISE = "SELECT * FROM BOOK WHERE cod_cruise=?";//SQL query to check if a cruise is associated with any booking in the database by its unique identifier (code)

	public DBImplementationCruise() {
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
			System.out.println("Error al intentar abrir la BD");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Cruise> getAllCruise() {//this method retrieves all cruises from the database 
		//by executing the SQLSELECTALL query and returns a list of Cruise objects representing the retrieved cruises
		List<Cruise> cruises = new ArrayList<>();
		Cruise cruise;
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTALL);
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				//creates a new Cruise object using the data retrieved from the database and adds it to the cruises list
				TypeCruise type = TypeCruise.valueOf(resultset.getString("type_cruise").toUpperCase());//this line converts the string value of the "type_cruise" column from the database to an enum constant of the TypeCruise enum
				//ensuring that the string is in uppercase to match the enum constant names
				cruise = new Cruise(Integer.parseInt(resultset.getString("cod_cruise")), type, resultset.getString("name_cruise"),
						resultset.getInt("num_rooms"), resultset.getInt("capacity_max"));//this line creates a new Cruise object using the retrieved data, including the cruise code, type, name, number of rooms, and maximum capacity
				
				cruises.add(cruise);
			}
			resultset.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());//Esta en español CUIDADOOOO
		}
		return cruises;
	}

	@Override
	public boolean deleteCruise(String id) {//this method deletes a cruise from the database 
		//based on its unique identifier (code) by executing the SQLDELETEBYCODE query and returns a boolean indicating whether the deletion was successful or not
		boolean deletePerformed = false;
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLDELETEBYCODE);
			statement.setString(1, id);
			if (statement.executeUpdate() > 0) {
				deletePerformed = true;
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return deletePerformed;
	}

	@Override
	public Cruise getCruiseByCode(String id) {//this method retrieves a cruise from the database
		//based on its unique identifier (code) by executing the SQLSELECTBYCODE query and returns a Cruise object representing the retrieved cruise, or null if no cruise is found with the provided code
		Cruise cruise = null;
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTBYCODE);
			statement.setString(1, id);
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				TypeCruise type = TypeCruise.valueOf(resultset.getString("type_cruise").toUpperCase());
				cruise = new Cruise(Integer.parseInt(resultset.getString("cod_cruise")), type, resultset.getString("name_cruise"),
						resultset.getInt("num_rooms"), resultset.getInt("capacity_max"));
			}
			resultset.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return cruise;
	}

	@Override
	public boolean updateCruiseByCode(Cruise cruise) {//this method updates the information of a cruise in the database 
		//based on its unique identifier (code) by executing the SQLUPDATEBYCODE query with the provided Cruise object's data and returns a boolean indicating whether the update was successful or not
		boolean updatePerformed = false;
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLUPDATEBYCODE);
			statement.setString(1, cruise.getTypeCruise().toString());
			statement.setString(2, cruise.getNameCruise());
			statement.setInt(3, cruise.getNumRooms());
			statement.setInt(4, cruise.getCapacityMax());
			statement.setInt(5, cruise.getCodCruise());

			if (statement.executeUpdate() > 0) {
				updatePerformed = true;
			}
			statement.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return updatePerformed;
	}

	@Override
	public boolean insertCruise(Cruise cruise) {//this method inserts a new cruise into the database 
		//by executing the SQLINSERT query with the provided Cruise object's data and returns a boolean indicating whether the insertion was successful or not
		boolean insertPerformed = false;
		this.openConnection();

		try {
			statement = connection.prepareStatement(SQLINSERT);
			statement.setString(1, cruise.getNameCruise());
			statement.setString(2, cruise.getTypeCruise().toString());
			statement.setInt(3, cruise.getNumRooms());
			statement.setInt(4, cruise.getCapacityMax());

			if (statement.executeUpdate() > 0) {
				insertPerformed = true;
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return insertPerformed;
	}

	@Override
	public boolean checkCruiseInWorker(String id) {//this method checks if a cruise is associated with any worker in the database
		boolean cruiseExistInWorker = false;
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTWORKERBYCRUISE);
			statement.setString(1, id);
			ResultSet resultset = statement.executeQuery();
			if (resultset.next()) {
				cruiseExistInWorker = true;
			}
			resultset.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return cruiseExistInWorker;
	}

	@Override
	public boolean checkCruiseInBook(String id) {//this method checks if a cruise is associated with any booking in the database 
		//by executing the SQLSELECTBOOKBYCREUISE query with the provided cruise code and returns a boolean indicating whether the cruise is associated with at least one booking or not
		boolean cruiseExistInBook = false;
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTBOOKBYCREUISE);
			statement.setString(1, id);
			ResultSet resultset = statement.executeQuery();
			if (resultset.next()) {
				cruiseExistInBook = true;
			}
			resultset.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return cruiseExistInBook;
	}

}
