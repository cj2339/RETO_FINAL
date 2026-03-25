package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public interface AdministratorDAO {
	public boolean checkUser(Administrator user);
	public boolean insertUser(Administrator user);
	public ArrayList<String> getCruiseCodes(); 
	public ArrayList<String> getWorkerCodes();
	public ArrayList<String> getClientCodes();
	public boolean deleteCruise(String id);
    public boolean deleteWorker(String id);
    public boolean deleteClient(String id);
	public boolean updatePassword(String adminName, String newPassword);
}