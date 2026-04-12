package model;

import java.util.List;

public interface AdministratorDAO {
	public List<Administrator> getAllAdministrators();
	public Administrator getAdministratorByName(String name);
	public boolean updateAdministrator(Administrator admin);
	public boolean deleteAdministrator(String name);
	public boolean checkUser(Administrator user);
	public boolean checkAdminExists(String name);
	public boolean insertUser(Administrator user);
	public boolean updatePassword(String adminName, String newPassword);
}