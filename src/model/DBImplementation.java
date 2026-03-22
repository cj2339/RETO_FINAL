package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class DBImplementation implements UserDAO{
		private Connection con;
		private PreparedStatement stmt;

		private ResourceBundle configFile;
		private String driverDB;
		private String urlDB;
		private String userDB;
		private String passwordDB;

		
		final String SQL = "SELECT * FROM user WHERE name = ? AND password = ?";		
		final String sql1 = "SELECT * FROM usuario WHERE nombre = ?";
		final String sqlInsert = "INSERT INTO usuario VALUES (?,?)";
		final String SQLCONSULTA = "SELECT * FROM usuario";
		final String SQLBORRAR = "DELETE FROM usuario WHERE nombre=?";
		final String SQLMODIFICAR = "UPDATE usuario SET contrasena=? WHERE nombre=?";
		
		public DBImplementation() {
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
				System.out.println("Error al intentar abrir la BD");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public boolean checkUser(User user){
			boolean existe=false;
			this.openConnection();
			try {
				stmt = con.prepareStatement(SQL);
	            stmt.setString(1, user.getName());
	            stmt.setString(2, user.getPassword());
	            ResultSet resultado = stmt.executeQuery();

	            if (resultado.next()) {
	                existe = true;
	            }
	            resultado.close();
	            stmt.close();
	            con.close();
	        } catch (SQLException e) {
	            System.out.println("Error al verificar credenciales: " + e.getMessage());
	        }
	        return existe;
	    }
		
		public boolean checkUser1(User user){
			boolean existe=false;
			this.openConnection();
			
			try {
				stmt = con.prepareStatement(sql1);
	            stmt.setString(1, user.getName());
	            ResultSet resultado = stmt.executeQuery();

	            if (resultado.next()) {
	                existe = true;
	            }
	            resultado.close();
	            stmt.close();
	            con.close();

	        } catch (SQLException e) {
	            System.out.println("Error al verificar credenciales: " + e.getMessage());
	        }

	        return existe;
	    }
		
		public boolean insertUser(User user) {
			boolean ok=false;
			if (!checkUser1(user))
			{
				this.openConnection();
				try {
					stmt = con.prepareStatement(sqlInsert);
					stmt.setString(1, user.getName());
					stmt.setString(2, user.getPassword());
					if (stmt.executeUpdate()>0) {
						ok=true;
					}			
		            stmt.close();
		            con.close();
				  } catch (SQLException e) {
		             System.out.println("Error al verificar credenciales: " + e.getMessage());
		        }
			}
				return ok;		
		}

		
} 
