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
    private String urlDB;
    private String userDB;
    private String passwordDB;

    final String SQL_CHECK_LOGIN = "SELECT * FROM administrator WHERE name_admin = ? AND password_admin = ?";
    final String SQL_INSERT_ADMIN = "INSERT INTO administrator VALUES (?,?)";
    final String SQL_GET_ALL_ADMINS = "SELECT * FROM administrator";
    final String SQL_GET_ADMIN_BY_NAME = "SELECT * FROM administrator WHERE name_admin = ?";
    final String SQL_UPDATE_ADMIN_PASSWORD = "UPDATE administrator SET password_admin = ? WHERE name_admin = ?";
    final String SQL_DELETE_ADMIN = "DELETE FROM administrator WHERE name_admin = ?";

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

    @Override
    public boolean insertUser(Administrator user) {
        if (checkAdminExists(user.getName())) return false;

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

    @Override
    public List<Administrator> getAllAdministrators() {
        List<Administrator> list = new ArrayList<>();
        openConnection();
        try {
            stmt = con.prepareStatement(SQL_GET_ALL_ADMINS);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Administrator(
                        rs.getString("name_admin"),
                        rs.getString("password_admin")
                ));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving admins: " + e.getMessage());
        }
        return list;
    }

    @Override
    public Administrator getAdministratorByName(String name) {
        Administrator admin = null;
        openConnection();
        try {
            stmt = con.prepareStatement(SQL_GET_ADMIN_BY_NAME);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                admin = new Administrator(
                        rs.getString("name_admin"),
                        rs.getString("password_admin")
                );
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving admin: " + e.getMessage());
        }
        return admin;
    }

    @Override
    public boolean updateAdministrator(Administrator admin) {
        return updatePassword(admin.getName(), admin.getPassword());
    }

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