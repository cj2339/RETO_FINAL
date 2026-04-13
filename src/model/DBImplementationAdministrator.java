package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DBImplementationAdministrator implements AdministratorDAO {

    private Connection con;
    private PreparedStatement stmt;

    private ResourceBundle configFile;
    //tengo que preguntar lo del driverDB, porque me da error y lo he quitado
    private String urlDB;//the URL of the database, which is read from the configuration file
    private String userDB;//the username for the database connection, which is read from the configuration file
    private String passwordDB;//the password for the database connection, which is read from the configuration file

    final String SQL_CHECK_LOGIN = "SELECT * FROM administrator WHERE name_admin = ? AND password_admin = ?";//SQL query to check if an administrator with the given name and password exists in the database
    final String SQL_INSERT_ADMIN = "INSERT INTO administrator VALUES (?,?)";//SQL query to insert a new administrator into the database with the provided name and password
    final String SQL_GET_ALL_ADMINS = "SELECT * FROM administrator";//SQL query to retrieve all administrators from the database
    final String SQL_GET_ADMIN_BY_NAME = "SELECT * FROM administrator WHERE name_admin = ?";//SQL query to retrieve an administrator from the database by their name
    final String SQL_UPDATE_ADMIN_PASSWORD = "UPDATE administrator SET password_admin = ? WHERE name_admin = ?";//SQL query to update the password of an administrator in the database based on their name
    final String SQL_DELETE_ADMIN = "DELETE FROM administrator WHERE name_admin = ?";//SQL query to delete an administrator from the database by their name

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

    @Override
    public boolean checkUser(Administrator administrator) {
    	//this little method checks if an administrator with the given name and password exists in the database 
    	//by executing the SQL_CHECK_LOGIN query and returns true if a matching record is found, otherwise returns false
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
            System.out.println("Error verifying credentials: " + e.getMessage());//if there is an error while executing the query, it prints an error message to the console
        }
        return exists;
    }

    @Override
    public boolean checkAdminExists(String adminName) {
    	//this method checks if an administrator with the given name exists in the database
    	//by executing the SQL_GET_ADMIN_BY_NAME query and returns true if a matching record is found, otherwise returns false
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
            System.out.println("Error checking for existence: " + e.getMessage());//if there is an error while executing the query, it prints an error message to the console
        }
        return exists;
    }

    @Override
    public boolean insertUser(Administrator user) {
    	//this method inserts a new administrator into the database
    	//by executing the SQL_INSERT_ADMIN query with the provided name and password
        if (checkAdminExists(user.getName())) return false;

        boolean ok = false;
        openConnection();
        try {
            stmt = con.prepareStatement(SQL_INSERT_ADMIN);
            stmt.setString(1, user.getName());//sets the first parameter of the SQL query to the name of the administrator
            stmt.setString(2, user.getPassword());//sets the second parameter of the SQL query to the password of the administrator
            ok = stmt.executeUpdate() > 0;//executes the SQL query and checks if it affected any rows in the database, returning true if the insertion was successful, otherwise false
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error inserting admin: " + e.getMessage());
        }
        return ok;
    }

    @Override
    public List<Administrator> getAllAdministrators() {
    	//this method retrieves all administrators from the database by executing the SQL_GET_ALL_ADMINS query
        List<Administrator> list = new ArrayList<>();
        openConnection();
        try {
            stmt = con.prepareStatement(SQL_GET_ALL_ADMINS);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Administrator(//creates a new Administrator object for each record in the result set and adds it to the list
                        rs.getString("name_admin"),
                        rs.getString("password_admin")
                ));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving admins: " + e.getMessage());//if there is an error while executing the query, it prints an error message to the console
        }
        return list;
    }

    @Override
    public Administrator getAdministratorByName(String name) {
    	//this method retrieves an administrator from the database
    	//by their name by executing the SQL_GET_ADMIN_BY_NAME query with the provided name
        Administrator admin = null;
        openConnection();
        try {
            stmt = con.prepareStatement(SQL_GET_ADMIN_BY_NAME);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                admin = new Administrator(//creates a new Administrator object with the retrieved name and password from the result set
                        rs.getString("name_admin"),
                        rs.getString("password_admin")
                );
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving admin: " + e.getMessage());//if there is an error while executing the query, it prints an error message to the console
        }
        return admin;
    }

    @Override
    public boolean updateAdministrator(Administrator admin) {//this method updates an administrator's information in the database 
    														//by calling the updatePassword method with the administrator's name and new password
        return updatePassword(admin.getName(), admin.getPassword());
    }

    @Override
    public boolean updatePassword(String adminName, String newPassword) {//this method updates the password of an administrator in the database 
    	//based on their name by executing the SQL_UPDATE_ADMIN_PASSWORD query with the provided name and new password
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

    @Override
    public boolean deleteAdministrator(String name) {//this method deletes an administrator from the database
    	//by their name by executing the SQL_DELETE_ADMIN query with the provided name
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