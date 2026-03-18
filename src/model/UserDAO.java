package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public interface UserDAO {
	public boolean comprobarUsuario(User usuario);
	public boolean insertarUsuario(User usuario);
}
