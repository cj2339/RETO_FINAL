package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public interface AdministratorDAO {
	public boolean checkUser(Administrator user);
	public boolean insertUser(Administrator user);
	public ArrayList<String> getWorkerCodes();
	public ArrayList<String> getClientCodes();
    public boolean deleteWorker(String id);
    public boolean deleteClient(String id);
    public boolean deleteBook(String codCruise, String idClient);
	public boolean updatePassword(String adminName, String newPassword);
}