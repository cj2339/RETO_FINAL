package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public interface AdministratorDAO {
	public boolean checkUser(Administrator user);
	public boolean insertUser(Administrator user);
}
 