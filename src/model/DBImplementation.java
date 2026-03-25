 package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class DBImplementation implements AdministratorDAO{
		private Connection con;
		private PreparedStatement stmt;

		private ResourceBundle configFile;
		private String driverDB;
		private String urlDB;
		private String userDB;
		private String passwordDB;

		
		final String SQL = "SELECT * FROM administrator WHERE name_admin = ? AND password_admin = ?";		
		final String sql1 = "SELECT * FROM administrator WHERE nombre_admin = ?";
		final String SQLINSERTADMIN = "INSERT INTO administrator VALUES (?,?)";
		final String SQLUPDATE = "UPDATE administrator SET password_admin=? WHERE name_admin=?";
		final String SQLSELECTADMIN = "SELECT * FROM administrator";
		final String SQLSELECTCRUISE = "SELECT * FROM cruise";
		final String SQLSELECTCRUISE2 = "SELECT * FROM cruise WHERE cod_cruise=?";
		final String SQLSELECTWORKER = "SELECT * FROM worker";
		final String SQLSELECTCLIENT = "SELECT * FROM client";
		final String SQLDELETEADMIN = "DELETE FROM administrator WHERE nombre_admin=?";
		final String SQLDELETECRUISE = "DELETE FROM cruise WHERE cod_cruise = ?";
		final String SQLDELETECLIENT = "DELETE FROM client WHERE id_client = ?";
		final String SQLDELETEWORKER = "DELETE FROM worker WHERE id_worker = ?";
		final String SQLGETCRUISE = "SELECT * FROM cruise";
		
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

		public boolean checkUser(Administrator administrator){
			boolean existe=false;
			this.openConnection();
			try {
				stmt = con.prepareStatement(SQL);
	            stmt.setString(1, administrator.getName());
	            stmt.setString(2, administrator.getPassword());
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
		
		public boolean checkUser1(Administrator user){
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
		
		public boolean checkCruise(Cruise cruise) {
			boolean exists=false;
			this.openConnection();
			try {
				stmt=con.prepareStatement(SQLSELECTCRUISE2);
				stmt.setString(1, cruise.getCodCruise());
				ResultSet result=stmt.executeQuery();
				
				if(result.next()) {
					exists=true;
				}
				result.close();
				stmt.close();
				con.close();
			}catch (SQLException e) {
	            System.out.println("Error verifying credentials: " + e.getMessage());
	        }
			return exists;
		}
		
		public boolean insertUser(Administrator user) {
			boolean ok=false;
			if (!checkUser1(user))
			{
				this.openConnection(); 
				try {
					stmt = con.prepareStatement(SQLINSERTADMIN);
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

		@Override
		public ArrayList<String> getCruiseCodes() {
		    ArrayList<String> codes = new ArrayList<>();
		    this.openConnection();
		    try {
		        stmt = con.prepareStatement(SQLSELECTCRUISE);
		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            codes.add(rs.getString("cod_cruise"));
		        }
		        rs.close();
		        stmt.close();
		        con.close();
		    } catch (SQLException e) {
		        System.out.println("Error obtaining cruises: " + e.getMessage());
		    }
		    return codes;
		}
		
		
		public List<Cruise> getAllCruises() {
		    List<Cruise> list = new ArrayList<>();
		    Cruise c;
		    this.openConnection();
		    try {
		        stmt = con.prepareStatement(SQLGETCRUISE);
		        ResultSet rs = stmt.executeQuery();

		        while (rs.next()) {
		            TypeCruise type = TypeCruise.valueOf(
		                rs.getString("type_cruise").toUpperCase()
		            );
		            	c = new Cruise(
		                rs.getString("cod_cruise"),
		                type,
		                rs.getString("name_cruise"),
		                rs.getInt("num_rooms"),
		                rs.getInt("capacity_max")
		            );
		            list.add(c);
		        }
		        rs.close();
		        stmt.close();
		        con.close();
		    } catch (SQLException e) {
		        System.out.println("Error al cargar cruceros: " + e.getMessage());
		    }
		    return list;
		}

		@Override
		public ArrayList<String> getWorkerCodes() {
		    ArrayList<String> codes = new ArrayList<>();
		    this.openConnection();
		    try {
		        stmt = con.prepareStatement(SQLSELECTWORKER);
		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            codes.add(rs.getString("id_worker"));
		        }
		        rs.close();
		        stmt.close();
		        con.close();
		    } catch (SQLException e) {
		        System.out.println("Error obtaining cruises: " + e.getMessage());
		    }
		    return codes;
		}

		@Override
		public ArrayList<String> getClientCodes() {
		    ArrayList<String> codes = new ArrayList<>();
		    this.openConnection();
		    try {
		        stmt = con.prepareStatement(SQLSELECTCLIENT);
		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            codes.add(rs.getString("id_client"));
		        }
		        rs.close();
		        stmt.close();
		        con.close();
		    } catch (SQLException e) {
		        System.out.println("Error obtaining cruises: " + e.getMessage());
		    }
		    return codes;
		}
		
		@Override
		public boolean deleteCruise(String id) {
		    boolean ok = false;
		    this.openConnection();
		    try {
		        stmt = con.prepareStatement(SQLDELETECRUISE);
		        stmt.setString(1, id);
		        if (stmt.executeUpdate() > 0) ok = true;
		        stmt.close();
		        con.close();
		    } catch (SQLException e) {
		        System.out.println("Error: " + e.getMessage());
		    }
		    return ok;
		}

		@Override
		public boolean deleteClient(String id) {
		    boolean ok = false;
		    this.openConnection();
		    try {
		        stmt = con.prepareStatement(SQLDELETECLIENT);
		        stmt.setString(1, id);
		        if (stmt.executeUpdate() > 0) ok = true;
		        stmt.close();
		        con.close();
		    } catch (SQLException e) {
		        System.out.println("Error: " + e.getMessage());
		    }
		    return ok;
		}

		@Override
		public boolean deleteWorker(String id) {
		    boolean ok = false;
		    this.openConnection();
		    try {
		        stmt = con.prepareStatement(SQLDELETEWORKER);
		        stmt.setString(1, id);
		        if (stmt.executeUpdate() > 0) ok = true;
		        stmt.close();
		        con.close();
		    } catch (SQLException e) {
		        System.out.println("Error: " + e.getMessage());
		    }
		    return ok;
		}
			
}
