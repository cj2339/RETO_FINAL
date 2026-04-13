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
	
	AdministratorDAO daoAdministrator = new DBImplementationAdministrator();
	CruiseDAO daoCruise = new DBImplementationCruise();
	WorkerDAO daoWorker = new DBImplementationWorker();
	ClientDAO daoClient = new DBImplementationClient();
	private String loggedInAdminName;

	// Cruise CRUD Methods
	public List<Cruise> getAllCruise() {
		return daoCruise.getAllCruise();
	}

	public boolean deleteCruise(String id) {
		return daoCruise.deleteCruise(id);
	}

	public boolean updateCruiseByCode(Cruise cruise) {
		return daoCruise.updateCruiseByCode(cruise);
	}

	public Cruise getCruiseByCode(String id) {
		return daoCruise.getCruiseByCode(id);
	}

	public boolean insertCruise(Cruise cruise) {
		return daoCruise.insertCruise(cruise);
	}

	public boolean checkCruiseInWorker(String id) {
		return daoCruise.checkCruiseInWorker(id);
	}

	public boolean checkCruiseInBook(String id) {
		return daoCruise.checkCruiseInBook(id);
	}

	// Administrator CRUD Methods
	public List<Administrator> getAllAdministrators() {
		return daoAdministrator.getAllAdministrators();
	}

	public boolean insertAdministrator(Administrator admin) {
		return daoAdministrator.insertUser(admin);
	}

	public boolean updateAdministrator(Administrator admin) {
		return daoAdministrator.updateAdministrator(admin);
	}

	public boolean deleteAdministrator(String name) {
		return daoAdministrator.deleteAdministrator(name);
	}

	public boolean checkAdminExists(String name) {
		return daoAdministrator.checkAdminExists(name);
	}

	// Client CRUD Methods
	public List<Client> getAllClient() {
		return daoClient.getAllClient();
	}

	public boolean deleteClient(Client client) {
		return daoClient.deleteClient(client);
	}

	public Client getClientByCode(Client client) {
		return daoClient.getClientByCode(client);
	}

	public boolean updateClientByCode(Client client) {
		return daoClient.updateClientByCode(client);
	}

	public boolean insertClient(Client client) {
		return daoClient.insertClient(client);
	}

	public boolean checkClientInBook(String id) {
		return daoClient.checkClientInBook(id);
	}

	// worker CRUD Methods
//	public ArrayList<String> getWorkerCodes() {
//		return daoWorker.getWorkerCodes();
//	}

	public List<Worker> getAllWorker() {
		return daoWorker.getAllWorker();
	}

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

	public String getLoggedInAdminName() {
		return loggedInAdminName;
	}

	public void setLoggedInAdminName(String loggedInAdminName) {
		this.loggedInAdminName = loggedInAdminName;
	}

	public boolean updatePassword(String adminName, String newPassword) {
		return daoAdministrator.updatePassword(adminName, newPassword);
	}

	// Windows Methods
	public void viewWindow() {
		StartWindow win = new StartWindow(this);
		win.setVisible(true);
	}
}