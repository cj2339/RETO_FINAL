package controller;

import java.util.List;

import model.Administrator;
import model.AdministratorDAO;
import model.ClientDAO;
import model.Cruise;
import model.CruiseDAO;
import model.DBImplementationClient;
import model.DBImplementationAdministrator;
import model.DBImplementationCruise;
import model.DBImplementationWorker;
import model.Worker;
import model.WorkerDAO;
import view.StartWindow;
import model.Client;

public class LoginController {

	AdministratorDAO daoAdministrator = new DBImplementationAdministrator();
	CruiseDAO daoCruise = new DBImplementationCruise();
	WorkerDAO daoWorker = new DBImplementationWorker();
	ClientDAO daoClient = new DBImplementationClient();
	private String loggedInAdminName;

	// Cruise CRUD Methods
	/**
	 * Retrieves a list of all cruises by calling the getAllCruise method of the
	 * CruiseDAO implementation and returns it.
	 * 
	 * @return
	 */
	public List<Cruise> getAllCruise() {
		return daoCruise.getAllCruise();
	}

	/**
	 * Deletes a cruise by calling the deleteCruise method of the CruiseDAO
	 * implementation with the provided id and returns the result.
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteCruise(String id) {
		return daoCruise.deleteCruise(id);
	}

	/**
	 * Updates a cruise by calling the updateCruiseByCode method of the CruiseDAO
	 * implementation with the provided cruise object and returns the result.
	 * 
	 * @param cruise
	 * @return
	 */
	public boolean updateCruiseByCode(Cruise cruise) {
		return daoCruise.updateCruiseByCode(cruise);
	}

	/**
	 * Retrieves a cruise by calling the getCruiseByCode method of the CruiseDAO
	 * implementation with the provided id and returns it.
	 * 
	 * @param id
	 * @return
	 */
	public Cruise getCruiseByCode(String id) {
		return daoCruise.getCruiseByCode(id);
	}

	/**
	 * Inserts a new cruise by calling the insertCruise method of the CruiseDAO
	 * implementation with the provided cruise object and returns the result.
	 * 
	 * @param cruise
	 * @return
	 */
	public boolean insertCruise(Cruise cruise) {
		return daoCruise.insertCruise(cruise);
	}

	/**
	 * Checks if a cruise is associated with any worker by calling the
	 * checkCruiseInWorker method of the CruiseDAO implementation with the provided
	 * id and returns the result.
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkCruiseInWorker(String id) {
		return daoCruise.checkCruiseInWorker(id);
	}

	/**
	 * Checks if a cruise is associated with any booking by calling the
	 * checkCruiseInBook method of the CruiseDAO implementation with the provided id
	 * and returns the result.
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkCruiseInBook(String id) {
		return daoCruise.checkCruiseInBook(id);
	}

	// Administrator CRUD Methods
	/**
	 * Retrieves a list of all administrators by calling the getAllAdministrators
	 * method of the AdministratorDAO implementation and returns it.
	 * 
	 * @return
	 */
	public List<Administrator> getAllAdministrators() {
		return daoAdministrator.getAllAdministrators();
	}

	/**
	 * Inserts a new administrator by calling the insertUser method of the
	 * AdministratorDAO implementation with the provided administrator object and
	 * returns the result.
	 * 
	 * @param admin
	 * @return
	 */
	public boolean insertAdministrator(Administrator admin) {
		return daoAdministrator.insertUser(admin);
	}

	/**
	 * Updates an administrator by calling the updateAdministrator method of the
	 * AdministratorDAO implementation with the provided administrator object and
	 * returns the result.
	 * 
	 * @param admin
	 * @return
	 */
	public boolean updateAdministrator(Administrator admin) {
		return daoAdministrator.updateAdministrator(admin);
	}

	/**
	 * Deletes an administrator by calling the deleteAdministrator method of the
	 * AdministratorDAO implementation with the provided name and returns the
	 * result.
	 * 
	 * @param name
	 * @return
	 */
	public boolean deleteAdministrator(String name) {
		return daoAdministrator.deleteAdministrator(name);
	}

	/**
	 * Checks if an administrator exists by calling the checkAdminExists method of
	 * the AdministratorDAO implementation with the provided name and returns the
	 * result.
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkAdminExists(String name) {
		return daoAdministrator.checkAdminExists(name);
	}

	// Client CRUD Methods

	/**
	 * Retrieves a list of all clients by calling the getAllClient method of the
	 * ClientDAO implementation and returns it.
	 * 
	 * @return
	 */
	public List<Client> getAllClient() {
		return daoClient.getAllClient();
	}

	/**
	 * Deletes a client by calling the deleteClient method of the ClientDAO
	 * implementation with the provided client object and returns the result.
	 * 
	 * @param client
	 * @return
	 */
	public boolean deleteClient(Client client) {
		return daoClient.deleteClient(client);
	}

	/**
	 * Retrieves a client by calling the getClientByCode method of the ClientDAO
	 * implementation with the provided client object and returns it.
	 * 
	 * @param client
	 * @return
	 */
	public Client getClientByCode(Client client) {
		return daoClient.getClientByCode(client);
	}

	/**
	 * Updates a client by calling the updateClientByCode method of the ClientDAO
	 * implementation with the provided client object and returns the result.
	 * 
	 * @param client
	 * @return
	 */
	public boolean updateClientByCode(Client client) {
		return daoClient.updateClientByCode(client);
	}

	/**
	 * Inserts a new client by calling the insertClient method of the ClientDAO
	 * implementation with the provided client object and returns the result.
	 * 
	 * @param client
	 * @return
	 */
	public boolean insertClient(Client client) {
		return daoClient.insertClient(client);
	}

	/**
	 * Checks if a client is associated with any booking by calling the
	 * checkClientInBook method of the ClientDAO implementation with the provided id
	 * and returns the result.
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkClientInBook(String id) {
		return daoClient.checkClientInBook(id);
	}

	// worker CRUD Methods
//	public ArrayList<String> getWorkerCodes() {
//		return daoWorker.getWorkerCodes();
//	}

	/**
	 * Retrieves a list of all workers by calling the getAllWorker method of the
	 * WorkerDAO implementation and returns it.
	 * 
	 * @return
	 */
	public List<Worker> getAllWorker() {
		return daoWorker.getAllWorker();
	}

	/**
	 * Inserts a new worker by calling the insertWorker method of the WorkerDAO
	 * implementation with the provided worker object and returns the result.
	 * 
	 * @param worker
	 * @return
	 */
	public boolean checkUser(Administrator user) {
		return daoAdministrator.checkUser(user);
	}
//
//	public boolean deleteWorker(String id) {
//		return dao.deleteWorker(id);
//	}
//
//	public boolean deleteClient(String id) {
//		return dao.deleteClient(id);
//	}
//
//	public boolean deleteBook(String codCruise, String idClient) {
//		return dao.deleteBook(codCruise, idClient);
//	}

//	public ArrayList<String> getClientCodes() {
//		return daoWorker.getClientCodes();
//	}
//
//	public boolean deleteWorker(String id) {
//		return daoWorker.deleteWorker(id);
//	}
//
//	public boolean deleteClient(String id) {
//		return daoWorker.deleteClient(id);
//	}
//
//	public boolean deleteBook(String codCruise, String idClient) {
//		return daoWorker.deleteBook(codCruise, idClient);
//	}

	/**
	 * Retrieves a worker by calling the getWorkerByCode method of the WorkerDAO
	 * implementation with the provided id and returns it.
	 * 
	 * @param id
	 * @return
	 */
	public String getLoggedInAdminName() {
		return loggedInAdminName;
	}

	/**
	 * Sets the name of the logged-in administrator by assigning the provided name
	 * to the loggedInAdminName variable.
	 * 
	 * @param loggedInAdminName
	 */
	public void setLoggedInAdminName(String loggedInAdminName) {
		this.loggedInAdminName = loggedInAdminName;
	}

	/**
	 * Updates the password of an administrator by calling the updatePassword method
	 * of the AdministratorDAO implementation with the provided administrator name
	 * and new password and returns the result.
	 * 
	 * @param adminName
	 * @param newPassword
	 * @return
	 */
	public boolean updatePassword(String adminName, String newPassword) {
		return daoAdministrator.updatePassword(adminName, newPassword);
	}

	// Windows Methods
	/**
	 * Creates a new instance of the StartWindow class, passing the current instance
	 * of LoginController as a parameter, and sets the window to be visible.
	 */
	public void viewWindow() {
		StartWindow win = new StartWindow(this);
		win.setVisible(true);
	}
}