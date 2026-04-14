package model;

import java.sql.Connection;
import java.sql.Date;
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

	final String SQLSELECTALL = "SELECT w.id_worker, w.service, w.name_worker, w.surname_worker, w.hiring_date, w.phone_number, w.email, "
			+ "w.age, w.spanish_language, w.cod_cruise, c.name_cruise, c.type_cruise, c.num_rooms, c.capacity_max FROM worker w "
			+ "JOIN cruise c ON w.cod_cruise=c.cod_cruise";//SQL query to select all workers from the database
	final String SQLDELETEBYCODE ="DELETE FROM worker WHERE id_worker = ?";
	final String SQLUPDATEBYCODE ="UPDATE worker SET id_worker";
	final String SQLINSERT="INSERT INTO worker VALUES(?,?,?,?,?,?,?,?,?,?)";
	
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
	public List<Worker> getAllWorker() {//this method retrieves all workers from the database 
		//by executing the SQLSELECTALL query and returns a list of Worker objects representing the retrieved workers
		List<Worker> workers=new ArrayList<>();
		
		this.openConnection();
		
		try {
			stmt = con.prepareStatement(SQLSELECTALL);
			ResultSet resultset = stmt.executeQuery();

			while (resultset.next()) {
				TypeWorker typeWorker=TypeWorker.valueOf(resultset.getString("service").toUpperCase());// Al conectarlo con la base de datos para que salga en mayusculas
				TypeCruise typeCruise=TypeCruise.valueOf(resultset.getString("type_cruise").toUpperCase());
				Cruise cruise=new Cruise(
						resultset.getInt("cod_cruise"),
						typeCruise,
						resultset.getString("name_cruise"),
						resultset.getInt("num_rooms"),
						resultset.getInt("capacity_max")
						);
				Worker worker=new Worker(
						resultset.getString("id_worker"), 
						typeWorker, 
						resultset.getString("name_worker"), 
						resultset.getString("surname_worker"),
						resultset.getDate("hiring_date"), 
						resultset.getString("phone_number"), 
						resultset.getString("email"), 
						resultset.getInt("age"), 
						resultset.getBoolean("spanish_language"),
						resultset.getBoolean("english_language"),
						cruise
				);
				workers.add(worker);//creates a new Worker object using the data retrieved from the database and adds it to the workers list
			}
			resultset.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error loading workers: " + e.getMessage());
		}
		return workers;
	}

	@Override
	public boolean deleteWorker(String id) {
		boolean deleted=false;
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLDELETEBYCODE);
			stmt.setString(1, id);
			if (stmt.executeUpdate() > 0) {
				deleted = true;
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return deleted;
	}

	@Override
	public boolean updateWorker(Worker worker) {
		boolean updated=false;
		this.openConnection();
		try {
			stmt=con.prepareStatement(SQLUPDATEBYCODE);
			stmt.setString(1, worker.getIdWorker());
			stmt.setString(2, worker.getService().toString());
			stmt.setString(3, worker.getName());
			stmt.setString(4, worker.getSurname());
			stmt.setDate(5, (Date) worker.getHiringDate());
			stmt.setString(6, worker.getPhoneNumber());
			stmt.setString(7, worker.getEmail());
			stmt.setInt(8, worker.getAge());
			stmt.setBoolean(9, worker.isSpanish());
			stmt.setBoolean(10, worker.isEnglish());
			
			if(stmt.executeUpdate()>0) {
				updated=true;
			}
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return updated;
	}

	@Override
	public boolean insertWorker(Worker worker) {
		boolean added=false;
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLINSERT);
			stmt.setString(1, worker.getIdWorker());
			stmt.setString(2, worker.getService().toString());
			stmt.setString(3, worker.getName());
			stmt.setString(4, worker.getSurname());
			stmt.setDate(5, (Date) worker.getHiringDate());
			stmt.setString(6, worker.getPhoneNumber());
			stmt.setString(7, worker.getEmail());
			stmt.setInt(8, worker.getAge());
			stmt.setBoolean(9, worker.isSpanish());
			stmt.setBoolean(10, worker.isEnglish());

			if (stmt.executeUpdate() > 0) {
				added = true;
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return added;
	}

}
