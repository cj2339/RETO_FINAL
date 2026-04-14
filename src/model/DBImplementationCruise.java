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

	final String SQLSELECTALL = "SELECT * FROM cruise";
	final String SQLDELETEBYCODE = "DELETE FROM cruise WHERE cod_cruise = ?";
	final String SQLSELECTBYCODE = "SELECT * FROM cruise WHERE cod_cruise=?";
	final String SQLUPDATEBYCODE = "UPDATE cruise set type_cruise=?,name_cruise=?, num_rooms=?,capacity_max=? WHERE cod_cruise=?";
	final String SQLINSERT = "INSERT INTO cruise(name_cruise, type_cruise, num_rooms, capacity_max) VALUES(?,?,?,?)";
	final String SQLSELECTWORKERBYCRUISE = "SELECT * FROM WORKER WHERE cod_cruise=?";
	final String SQLSELECTBOOKBYCREUISE = "SELECT * FROM BOOK WHERE cod_cruise=?";

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
	/**
	 * This method retrieves all cruises from the database by executing the
	 * SQLSELECTALL
	 */
	public List<Cruise> getAllCruise() {
		List<Cruise> cruises = new ArrayList<>();
		Cruise cruise;
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTALL);
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				TypeCruise type = TypeCruise.valueOf(resultset.getString("type_cruise").toUpperCase());
				cruise = new Cruise(Integer.parseInt(resultset.getString("cod_cruise")), type,
						resultset.getString("name_cruise"), resultset.getInt("num_rooms"),
						resultset.getInt("capacity_max"));

				cruises.add(cruise);
			}
			resultset.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());// Esta en español CUIDADOOOO
		}
		return cruises;
	}

	@Override
	/**
	 * This method deletes a cruise from the database based on the provided cruise
	 * ID. It opens a connection to the database, prepares a SQL statement to delete
	 * the cruise with the specified ID, and executes the update. If the deletion is
	 * successful (i.e., if at least one record is affected), it returns true;
	 * otherwise, it returns false. The method also handles any SQL exceptions that
	 * may occur during the process and ensures that all resources are properly
	 * closed after use.
	 */
	public boolean deleteCruise(String id) {
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
	/**
	 * This method retrieves a cruise from the database based on the provided cruise
	 * ID. It opens a connection to the database, prepares a SQL statement to select
	 * the cruise with the specified ID, and executes the query. If a matching
	 * record is found in the result set, it creates and returns a Cruise object
	 * representing the retrieved cruise. If no matching record is found, it returns
	 * null. The method also handles any SQL exceptions that may occur during the
	 * process and ensures that all resources are properly closed after use.
	 */
	public Cruise getCruiseByCode(String id) {
		Cruise cruise = null;
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTBYCODE);
			statement.setString(1, id);
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				TypeCruise type = TypeCruise.valueOf(resultset.getString("type_cruise").toUpperCase());
				cruise = new Cruise(Integer.parseInt(resultset.getString("cod_cruise")), type,
						resultset.getString("name_cruise"), resultset.getInt("num_rooms"),
						resultset.getInt("capacity_max"));
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
	/**
	 * This method updates the information of a cruise in the database based on the
	 * provided Cruise object. It opens a connection to the database, prepares a SQL
	 * statement to update the cruise with the specified information, and executes
	 * the update. If the update is successful (i.e., if at least one record is
	 * affected), it returns true; otherwise, it returns false. The method also
	 * handles any SQL exceptions that may occur during the process and ensures that
	 * all resources are properly closed after use.
	 */
	public boolean updateCruiseByCode(Cruise cruise) {
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
	/**
	 * This method inserts a new cruise into the database based on the provided
	 * Cruise object. It opens a connection to the database, prepares a SQL
	 * statement to insert the cruise with the specified information, and executes
	 * the update. If the insertion is successful (i.e., if at least one record is
	 * affected), it returns true; otherwise, it returns false. The method also
	 * handles any SQL exceptions that may occur during the process and ensures that
	 * all resources are properly closed after use.
	 */
	public boolean insertCruise(Cruise cruise) {
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
	/**
	 * This method checks if a cruise with the given identifier (id) is associated
	 * with at least one worker in the database. It opens a connection to the
	 * database, prepares a SQL statement to select workers associated with the
	 * specified cruise ID, and executes the query. If a matching record is found in
	 * the result set, it returns true; otherwise, it returns false. The method also
	 * handles any SQL exceptions that may occur during the process and ensures that
	 * all resources are properly closed after use.
	 */
	public boolean checkCruiseInWorker(String id) {
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
	/**
	 * This method checks if a cruise with the given identifier (id) is associated
	 * with at least one booking in the database. It opens a connection to the
	 * database, prepares a SQL statement to select bookings associated with the
	 * specified cruise ID, and executes the query. If a matching record is found in
	 * the result set, it returns true; otherwise, it returns false. The method also
	 * handles any SQL exceptions that may occur during the process and ensures that
	 * all resources are properly closed after use.
	 */
	public boolean checkCruiseInBook(String id) {
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
