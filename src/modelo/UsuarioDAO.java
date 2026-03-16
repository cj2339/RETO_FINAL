package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public interface UsuarioDAO {
	public boolean comprobarUsuario(Usuario usuario);
	public boolean insertarUsuario(Usuario usuario);
}
