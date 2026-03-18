package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public interface UserDAO {
	public boolean login(User user);
	public boolean checkUser(User user);
	public boolean insertUser(User user);
}
 