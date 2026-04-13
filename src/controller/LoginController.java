package controller;

import java.util.ArrayList;
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
	
	AdministratorDAO daoAdministrator = new DBImplementationAdministrator();// implementation of the AdministratorDAO interface
	CruiseDAO daoCruise = new DBImplementationCruise();//implementation of the CruiseDAO interface
	WorkerDAO daoWorker = new DBImplementationWorker();//implementation of the WorkerDAO interface
	ClientDAO daoClient = new DBImplementationClient();//implementation of the ClientDAO interface
	private String loggedInAdminName;

	// Cruise CRUD Methods
	public List<Cruise> getAllCruise() {//returns a list of all cruises by calling the getAllCruise method of the CruiseDAO implementation
		return daoCruise.getAllCruise();
	}

	public boolean deleteCruise(String id) {//deletes a cruise by calling the deleteCruise method of the CruiseDAO implementation with the provided id
		return daoCruise.deleteCruise(id);
	}

	public boolean updateCruiseByCode(Cruise cruise) {//updates a cruise by calling the updateCruiseByCode method of the CruiseDAO implementation with the provided cruise object
		return daoCruise.updateCruiseByCode(cruise);
	}

	public Cruise getCruiseByCode(String id) {//retrieves a cruise by calling the getCruiseByCode method of the CruiseDAO implementation with the provided id
		return daoCruise.getCruiseByCode(id);
	}

	public boolean insertCruise(Cruise cruise) {//inserts a new cruise by calling the insertCruise method of the CruiseDAO implementation with the provided cruise object
		return daoCruise.insertCruise(cruise);
	}

	public boolean checkCruiseInWorker(String id) {//checks if a cruise is associated with any worker by calling the checkCruiseInWorker method of the CruiseDAO implementation with the provided id
		return daoCruise.checkCruiseInWorker(id);
	}

	public boolean checkCruiseInBook(String id) {//checks if a cruise is associated with any booking by calling the checkCruiseInBook method of the CruiseDAO implementation with the provided id
		return daoCruise.checkCruiseInBook(id);
	}

	// Administrator CRUD Methods
	public List<Administrator> getAllAdministrators() {//returns a list of all administrators by calling the getAllAdministrators method of the AdministratorDAO implementation
		return daoAdministrator.getAllAdministrators();
	}

	public boolean insertAdministrator(Administrator admin) {//inserts a new administrator by calling the insertUser method of the AdministratorDAO implementation with the provided administrator object
		return daoAdministrator.insertUser(admin);
	}

	public boolean updateAdministrator(Administrator admin) {//updates an administrator by calling the updateAdministrator method of the AdministratorDAO implementation with the provided administrator object
		return daoAdministrator.updateAdministrator(admin);
	}

	public boolean deleteAdministrator(String name) {//deletes an administrator by calling the deleteAdministrator method of the AdministratorDAO implementation with the provided name
		return daoAdministrator.deleteAdministrator(name);
	}

	public boolean checkAdminExists(String name) {//checks if an administrator exists by calling the checkAdminExists method of the AdministratorDAO implementation with the provided name
		return daoAdministrator.checkAdminExists(name);
	}

	// Client CRUD Methods
	public List<Client> getAllClient() {//returns a list of all clients by calling the getAllClient method of the ClientDAO implementation
		return daoClient.getAllClient();
	}

	public boolean deleteClient(Client client) {//deletes a client by calling the deleteClient method of the ClientDAO implementation with the provided client object
		return daoClient.deleteClient(client);
	}

	public Client getClientByCode(Client client) {//retrieves a client by calling the getClientByCode method of the ClientDAO implementation with the provided client object
		return daoClient.getClientByCode(client);
	}

	public boolean updateClientByCode(Client client) {//updates a client by calling the updateClientByCode method of the ClientDAO implementation with the provided client object
		return daoClient.updateClientByCode(client);
	}

	public boolean insertClient(Client client) {//inserts a new client by calling the insertClient method of the ClientDAO implementation with the provided client object
		return daoClient.insertClient(client);
	}

	public boolean checkClientInBook(String id) {//checks if a client is associated with any booking by calling the checkClientInBook method of the ClientDAO implementation with the provided id
		return daoClient.checkClientInBook(id);
	}

	// worker CRUD Methods
//	public ArrayList<String> getWorkerCodes() {
//		return daoWorker.getWorkerCodes();
//	}

	public List<Worker> getAllWorker() {//returns a list of all workers by calling the getAllWorker method of the WorkerDAO implementation
		return daoWorker.getAllWorker();
	}

	public boolean checkUser(Administrator user) {//checks if a user exists by calling the checkUser method of the AdministratorDAO implementation with the provided user object
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

	public String getLoggedInAdminName() {//returns the name of the currently logged-in administrator
		return loggedInAdminName;
	}

	public void setLoggedInAdminName(String loggedInAdminName) {//sets the name of the currently logged-in administrator
		this.loggedInAdminName = loggedInAdminName;
	}

	public boolean updatePassword(String adminName, String newPassword) {//updates the password of an administrator by calling the updatePassword method of the AdministratorDAO implementation with the provided administrator name and new password
		return daoAdministrator.updatePassword(adminName, newPassword);
	}

	// Windows Methods
	public void viewWindow() {//creates and displays the start window by instantiating a new StartWindow object with the current LoginController instance and setting it to visible
		StartWindow win = new StartWindow(this);
		win.setVisible(true);
	}
}