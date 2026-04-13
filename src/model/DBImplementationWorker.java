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
	private Connection con;
	private PreparedStatement stmt;

	private ResourceBundle configFile;
	private String driverDB;
	private String urlDB;
	private String userDB;
	private String passwordDB;

	final String SQLSELECTALL = "SELECT * FROM worker";

	public DBImplementationWorker() {
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
			stmt = con.prepareStatement(SQLSELECTALL);
			ResultSet resultset = stmt.executeQuery();

			while (resultset.next()) {
				// Al conectarlo con la base de datos para que salga en mayusculas
				TypeWorker type=TypeWorker.valueOf(resultset.getString("service").toUpperCase());
				Language language=Language.valueOf(resultset.getString("language").toUpperCase());
				worker=new Worker(resultset.getString("id_worker"), type, resultset.getString("name_worker"), 
						resultset.getString("surname_worker"),resultset.getDate("hiringDate"), resultset.getString("phoneNumber"), 
						resultset.getString("email"), resultset.getInt("age"), language, resultset.getInt("cod_cruise"));
				workers.add(worker);
			}
			resultset.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error loading workers: " + e.getMessage());
		}
		return workers;
	}

}
