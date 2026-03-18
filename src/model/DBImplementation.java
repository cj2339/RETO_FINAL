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
	// Atributos
		private Connection con;
		private PreparedStatement stmt;

		// Los siguientes atributos se utilizan para recoger los valores del fich de
		// configuración
		private ResourceBundle configFile;
		private String driverBD;
		private String urlBD;
		private String userBD;
		private String passwordBD;

		// Sentencias SQL
		
		final String SQL = "SELECT * FROM usuario WHERE nombre = ? AND contrasena = ?";		
		final String sql1 = "SELECT * FROM usuario WHERE nombre = ?";
		final String sqlInsert = "INSERT INTO usuario VALUES (?,?)";
		final String SQLCONSULTA = "SELECT * FROM usuario";
		final String SQLBORRAR = "DELETE FROM usuario WHERE nombre=?";
		final String SQLMODIFICAR = "UPDATE usuario SET contrasena=? WHERE nombre=?";
		
		// Para la conexión utilizamos un fichero de configuaraci n, config que
		// guardamos en el paquete control:
		public DBImplementation() {
			this.configFile = ResourceBundle.getBundle("configClase");
			this.driverBD = this.configFile.getString("Driver");
			this.urlBD = this.configFile.getString("Conn");
			this.userBD = this.configFile.getString("DBUser");
			this.passwordBD = this.configFile.getString("DBPass");
		}

		private void openConnection() {
			try {
				con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);
			} catch (SQLException e) {
				System.out.println("Error al intentar abrir la BD");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public boolean comprobarUsuario(User usuario){
			// Abrimos la conexion
			boolean existe=false;
			this.openConnection();
			try {
				stmt = con.prepareStatement(SQL);
	            stmt.setString(1, usuario.getNombre());
	            stmt.setString(2, usuario.getContrasena());
	            ResultSet resultado = stmt.executeQuery();

	            //Si hay un resultado, el usuario existe
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
		public boolean comprobarUsuario1(User usuario){
			// Abrimos la conexion
			boolean existe=false;
			this.openConnection();
			
			try {
				stmt = con.prepareStatement(sql1);
	            stmt.setString(1, usuario.getNombre());
	            ResultSet resultado = stmt.executeQuery();

	            //Si hay un resultado, el usuario existe
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
		
		public boolean insertarUsuario(User usuario) {
			// TODO Auto-generated method stub
			boolean ok=false;
			if (!comprobarUsuario1(usuario))
			{
				this.openConnection();
				try {
					// Preparamos la sentencia stmt con la conexion y sentencia sql correspondiente
					stmt = con.prepareStatement(sqlInsert);
					stmt.setString(1, usuario.getNombre());
					stmt.setString(2, usuario.getContrasena());
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
