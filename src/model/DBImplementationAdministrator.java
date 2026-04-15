package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Concrete implementation of AdministratorDAO connecting to a database.
 * Handles all Administrator-related persistence operations.
 */
public class DBImplementationAdministrator implements AdministratorDAO {

	private Connection con;
	private PreparedStatement stmt;

	private ResourceBundle configFile;
	// tengo que preguntar lo del driverDB, porque me da error y lo he quitado
	private String urlDB;
	private String userDB;
	private String passwordDB;

	final String SQL_CHECK_LOGIN = "SELECT * FROM administrator WHERE name_admin = ? AND password_admin = ?";
	final String SQL_INSERT_ADMIN = "INSERT INTO administrator VALUES (?,?)";
	final String SQL_GET_ALL_ADMINS = "SELECT * FROM administrator";
	final String SQL_GET_ADMIN_BY_NAME = "SELECT * FROM administrator WHERE name_admin = ?";
	final String SQL_UPDATE_ADMIN_PASSWORD = "UPDATE administrator SET password_admin = ? WHERE name_admin = ?";
	final String SQL_DELETE_ADMIN = "DELETE FROM administrator WHERE name_admin = ?";

	/**
	 * Initializes the DAO and reads database connection settings from the configuration file.
	 */
	public DBImplementationAdministrator() {
		this.configFile = ResourceBundle.getBundle("configClass");
		this.urlDB = this.configFile.getString("Conn");
		this.userDB = this.configFile.getString("DBUser");
		this.passwordDB = this.configFile.getString("DBPass");
	}

	private void openConnection() {
		try {
			con = DriverManager.getConnection(urlDB, userDB, passwordDB);
		} catch (SQLException e) {
			System.out.println("Error opening the database: " + e.getMessage());
		}
	}

	/**
	 * This method checks if the provided administrator's credentials (name and
	 * password) exist in the database. It opens a connection to the database,
	 * prepares a SQL statement to check for the administrator's credentials, and
	 * executes the query. If a matching record is found, it returns true;
	 * otherwise, it returns false. It also handles any SQL exceptions that may
	 * occur during the process and ensures that all resources are properly closed
	 * after use.
	 * 
	 * @param administrator
	 * @return true if the administrator's credentials are valid, false otherwise
	 */
	@Override

	public boolean checkUser(Administrator administrator) {
		boolean exists = false;
		openConnection();
		try {
			stmt = con.prepareStatement(SQL_CHECK_LOGIN);
			stmt.setString(1, administrator.getName());
			stmt.setString(2, administrator.getPassword());
			ResultSet rs = stmt.executeQuery();
			exists = rs.next();
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error verifying credentials: " + e.getMessage());
		}
		return exists;
	}

	/**
	 * This method checks if an administrator with the given name already exists in
	 * the database. It opens a connection to the database, prepares a SQL statement
	 * to check for the administrator's name, and executes the query. If a matching
	 * record is found, it returns true; otherwise, it returns false. It also
	 * handles any SQL exceptions that may occur during the process and ensures that
	 * all resources are properly closed after use.
	 * 
	 * @param adminName
	 * @return true if an administrator with the given name exists, false otherwise
	 */
	@Override

	public boolean checkAdminExists(String adminName) {
		boolean exists = false;
		openConnection();
		try {
			stmt = con.prepareStatement(SQL_GET_ADMIN_BY_NAME);
			stmt.setString(1, adminName);
			ResultSet rs = stmt.executeQuery();
			exists = rs.next();
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error checking for existence: " + e.getMessage());
		}
		return exists;
	}

	/**
	 * This method inserts a new administrator into the database. It first checks if
	 * an administrator with the same name already exists by calling the
	 * checkAdminExists method. If an administrator with the same name exists, it
	 * returns false. Otherwise, it opens a connection to the database, prepares a
	 * SQL statement to insert the new administrator's name and password, and
	 * executes the update. If the insertion is successful, it returns true;
	 * otherwise, it returns false. It also handles any SQL exceptions that may
	 * occur during the process and ensures that all resources are properly closed
	 * after use.
	 * 
	 * @param user
	 * @return true if the administrator was successfully inserted, false otherwise
	 */
	@Override

	public boolean insertUser(Administrator user) {
		if (checkAdminExists(user.getName()))
			return false;

		boolean ok = false;
		openConnection();
		try {
			stmt = con.prepareStatement(SQL_INSERT_ADMIN);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());
			ok = stmt.executeUpdate() > 0;
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error inserting admin: " + e.getMessage());
		}
		return ok;
	}

	/**
	 * This method retrieves a list of all administrators from the database. It
	 * opens a connection to the database, prepares a SQL statement to select all
	 * records from the administrator table, and executes the query. It iterates
	 * through the result set and creates Administrator objects for each record,
	 * adding them to a list. Finally, it returns the list of administrators. It
	 * also handles any SQL exceptions that may occur during the process and ensures
	 * that all resources are properly closed after use.
	 * 
	 * @return a list of all administrators in the database
	 */

	@Override
	public List<Administrator> getAllAdministrators() {
		List<Administrator> list = new ArrayList<>();
		openConnection();
		try {
			stmt = con.prepareStatement(SQL_GET_ALL_ADMINS);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new Administrator(rs.getString("name_admin"), rs.getString("password_admin")));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error retrieving admins: " + e.getMessage());
		}
		return list;
	}

	/**
	 * This method retrieves an administrator from the database based on the
	 * provided name. It opens a connection to the database, prepares a SQL
	 * statement to select the record with the specified name, and executes the
	 * query. If a matching record is found, it creates an Administrator object with
	 * the retrieved name and password and returns it. If no matching record is
	 * found, it returns null. It also handles any SQL exceptions that may occur
	 * during the process and ensures that all resources are properly closed after
	 * use.
	 * 
	 * @param name the name of the administrator to retrieve
	 * @return an Administrator object if a matching record is found, null otherwise
	 */
	@Override
	public Administrator getAdministratorByName(String name) {

		Administrator admin = null;
		openConnection();
		try {
			stmt = con.prepareStatement(SQL_GET_ADMIN_BY_NAME);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				admin = new Administrator(rs.getString("name_admin"), rs.getString("password_admin"));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error retrieving admin: " + e.getMessage());
		}
		return admin;
	}

	/**
	 * This method updates an administrator's password in the database. It takes an
	 * Administrator object as a parameter, retrieves the administrator's name and
	 * new password, and opens a connection to the database. It prepares a SQL
	 * statement to update the password for the specified administrator name and
	 * executes the update. If the update is successful, it returns true; otherwise,
	 * it returns false. It also handles any SQL exceptions that may occur during
	 * the process and ensures that all resources are properly closed after use.
	 * 
	 * @param admin
	 * @return true if the administrator was successfully updated, false otherwise
	 */
	@Override

	public boolean updateAdministrator(Administrator admin) {
		return updatePassword(admin.getName(), admin.getPassword());
	}

	/**
	 * This method updates an administrator's password in the database based on the
	 * administrator's name. It takes the administrator's name and the new password
	 * as parameters, opens a connection to the database, prepares a SQL statement
	 * to update the password for the specified administrator name, and executes the
	 * update. If the update is successful, it returns true; otherwise, it returns
	 * false. It also handles any SQL exceptions that may occur during the process
	 * and ensures that all resources are properly closed after use. 
	 * 
	 * @param adminName
	 * @param newPassword
	 * @return true if the password was successfully updated, false otherwise
	 */
	@Override
	public boolean updatePassword(String adminName, String newPassword) {
		boolean ok = false;
		openConnection();
		try {
			stmt = con.prepareStatement(SQL_UPDATE_ADMIN_PASSWORD);
			stmt.setString(1, newPassword);
			stmt.setString(2, adminName);
			ok = stmt.executeUpdate() > 0;
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error updating password: " + e.getMessage());
		}
		return ok;
	}
	
	/**
	 * This method deletes an administrator from the database based on the
	 * administrator's name. It takes the administrator's name as a parameter,
	 * opens a connection to the database, prepares a SQL statement to delete the
	 * record with the specified administrator name, and executes the update. If the
	 * deletion is successful, it returns true; otherwise, it returns false. It also
	 * handles any SQL exceptions that may occur during the process and ensures that
	 * all resources are properly closed after use.
	 * 
	 * @param name
	 * @return true if the administrator was successfully deleted, false otherwise
	 */

	@Override
	public boolean deleteAdministrator(String name) {
		boolean ok = false;
		openConnection();
		try {
			stmt = con.prepareStatement(SQL_DELETE_ADMIN);
			stmt.setString(1, name);
			ok = stmt.executeUpdate() > 0;
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error deleting admin: " + e.getMessage());
		}
		return ok;
	}
}