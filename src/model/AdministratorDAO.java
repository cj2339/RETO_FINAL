package model;

import java.util.List;

public interface AdministratorDAO {
	public List<Administrator> getAllAdministrators();//returns a list of all administrators
	public Administrator getAdministratorByName(String name);//retrieves an administrator by their unique identifier (name)
	public boolean updateAdministrator(Administrator admin);//updates an administrator's information in the database based on their unique identifier (name)
	public boolean deleteAdministrator(String name);//deletes an administrator from the database by their unique identifier (name)
	public boolean checkUser(Administrator user);//checks if a user exists in the database by their name and password
	public boolean checkAdminExists(String name);//checks if an administrator with the given name exists in the database
	public boolean insertUser(Administrator user);//inserts a new administrator into the database
	public boolean updatePassword(String adminName, String newPassword);//updates an administrator's password in the database based on their unique identifier (name)
}