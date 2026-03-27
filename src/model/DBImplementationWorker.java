package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DBImplementationWorker implements WorkerDAO {
	private Connection connection;
	private PreparedStatement statement;

	private ResourceBundle configFile;
	private String driverDB;
	private String urlDB;
	private String userDB;
	private String passwordDB;

	final String SQLSELECTALL = "SELECT * FROM worker";
	final String SQLDELETEBYCODE = "DELETE FROM cruise WHERE cod_cruise = ?";
	final String SQLSELECTBYCODE = "SELECT * FROM cruise WHERE cod_cruise=?";
	final String SQLUPDATEBYCODE = "UPDATE cruise set type_cruise=?,name_cruise=?, num_rooms=?,capacity_max=? WHERE cod_cruise=?";
	final String SQLINSERT = "INSERT INTO CRUISE VALUES(?,?,?,?,?);";
	final String SQLSELECTWORKERBYCRUISE = "SELECT * FROM WORKER WHERE cod_cruise=?";
	final String SQLSELECTBOOKBYCREUISE = "SELECT * FROM BOOK WHERE cod_cruise=?";

	public DBImplementationWorker() {
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
			System.out.println("Error trying to open the DB");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Worker> getAllWorker() {
		Worker worker;
		List<Worker> workers=new ArrayList<>();
		this.openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTALL);
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				// Al conectarlo con la base de datos para que salga en mayusculas
				TypeWorker type=TypeWorker.valueOf(resultset.getString("type_worker").toUpperCase());
				worker=new Worker(resultset.getString("id_worker"), type, resultset.getString("name_worker"), resultset.getString("surname_worker"), resultset.getString("cod_cruise"));
				workers.add(worker);
			}
			resultset.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error al cargar trabajadores: " + e.getMessage());//Esta en español CUIDADOOOO
		}
		return workers;
	}

//	@Override
//	public boolean deleteCruise(String id) {
//		boolean deletePerformed = false;
//		this.openConnection();
//		try {
//			statement = connection.prepareStatement(SQLDELETEBYCODE);
//			statement.setString(1, id);
//			if (statement.executeUpdate() > 0) {
//				deletePerformed = true;
//			}
//			statement.close();
//			connection.close();
//		} catch (SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//		return deletePerformed;
//	}
//
//	@Override
//	public Cruise getCruiseByCode(String id) {
//		Cruise cruise = null;
//		this.openConnection();
//		try {
//			statement = connection.prepareStatement(SQLSELECTBYCODE);
//			statement.setString(1, id);
//			ResultSet resultset = statement.executeQuery();
//
//			while (resultset.next()) {
//				TypeCruise type = TypeCruise.valueOf(resultset.getString("type_cruise").toUpperCase());
//				cruise = new Cruise(resultset.getString("cod_cruise"), type, resultset.getString("name_cruise"),
//						resultset.getInt("num_rooms"), resultset.getInt("capacity_max"));
//			}
//			resultset.close();
//			statement.close();
//			connection.close();
//		} catch (SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//		return cruise;
//	}
//
//	@Override
//	public boolean updateCruiseByCode(Cruise cruise) {
//		boolean updatePerformed = false;
//		this.openConnection();
//		try {
//			statement = connection.prepareStatement(SQLUPDATEBYCODE);
//			statement.setString(1, cruise.getTypeCruise().toString());
//			statement.setString(2, cruise.getNameCruise());
//			statement.setInt(3, cruise.getNumRooms());
//			statement.setInt(4, cruise.getCapacityMax());
//			statement.setString(5, cruise.getCodCruise());
//
//			if (statement.executeUpdate() > 0) {
//				updatePerformed = true;
//			}
//			statement.close();
//			connection.close();
//
//		} catch (SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//		return updatePerformed;
//	}
//
//	@Override
//	public boolean insertCruise(Cruise cruise) {
//		boolean insertPerformed = false;
//		this.openConnection();
//
//		try {
//			statement = connection.prepareStatement(SQLINSERT);
//			statement.setString(1, cruise.getCodCruise());
//			statement.setString(2, cruise.getTypeCruise().toString());
//			statement.setString(3, cruise.getNameCruise());
//			statement.setInt(4, cruise.getNumRooms());
//			statement.setInt(5, cruise.getCapacityMax());
//
//			if (statement.executeUpdate() > 0) {
//				insertPerformed = true;
//			}
//			statement.close();
//			connection.close();
//		} catch (SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//		return insertPerformed;
//	}
//
//
//	@Override
//	public boolean checkCruiseInBook(String id) {
//		boolean cruiseExistInBook = false;
//		this.openConnection();
//		try {
//			statement = connection.prepareStatement(SQLSELECTBOOKBYCREUISE);
//			statement.setString(1, id);
//			ResultSet resultset = statement.executeQuery();
//			if (resultset.next()) {
//				cruiseExistInBook = true;
//			}
//			resultset.close();
//			statement.close();
//			connection.close();
//		} catch (SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//		return cruiseExistInBook;
//	}


}
