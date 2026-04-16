package model;

import java.util.List;

/**
 * Data Access Object interface for managing Administrator entities.
 * 
 * @author Iker
 */
public interface AdministratorDAO {
	/**
	 * Retrieves a list of all administrators by calling the getAllAdministrators
	 * method
	 * 
	 * @return
	 */
	public List<Administrator> getAllAdministrators();

	/**
	 * Retrieves an administrator by name by calling the getAdministratorByName
	 * method with the provided name and returns the result.
	 * 
	 * @param name
	 * @return
	 */
	public Administrator getAdministratorByName(String name);

	/**
	 * Updates an administrator by calling the updateAdministrator method with the
	 * provided administrator object and returns the result.
	 * 
	 * @param admin
	 * @return
	 */
	public boolean updateAdministrator(Administrator admin);

	/**
	 * Deletes an administrator by calling the deleteAdministrator method with the
	 * provided name and returns the result.
	 * 
	 * @param name
	 * @return
	 */
	public boolean deleteAdministrator(String name);

	/**
	 * Checks if an administrator exists by calling the checkAdminExists method with
	 * the provided name and returns the result.
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkUser(Administrator user);

	/**
	 * Checks if an administrator exists by calling the checkAdminExists method with
	 * the provided name and returns the result.
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkAdminExists(String name);

	/**
	 * Inserts a new administrator by calling the insertUser method with the
	 * provided administrator object and returns the result.
	 * 
	 * @param admin
	 * @return
	 */
	public boolean insertUser(Administrator user);

	/**
	 * Updates the password of an administrator by calling the updatePassword method
	 * with the provided administrator name and new password and returns the result.
	 * 
	 * @param adminName
	 * @param newPassword
	 * @return
	 */
	public boolean updatePassword(String adminName, String newPassword);
}