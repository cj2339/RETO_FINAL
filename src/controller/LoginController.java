package controller;

import view.StartWindow;

import model.DBImplementationAdministrator;
import model.DBImplementationCruise;
import model.DBImplementationWorker;
import model.Worker;
import model.WorkerDAO;

import java.util.ArrayList;
import java.util.List;

import model.Administrator;
import model.AdministratorDAO;
import model.Cruise;
import model.CruiseDAO;

public class LoginController {
	AdministratorDAO dao = new DBImplementationAdministrator();
	CruiseDAO daoCruise = new DBImplementationCruise();
	WorkerDAO daoWorker = new DBImplementationWorker();
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
	public boolean checkCruiseInWorker(String id)
	{
		return daoCruise.checkCruiseInWorker(id);
	}
	public boolean checkCruiseInBook(String id)
	{
		return daoCruise.checkCruiseInBook(id);
	}
	
	// Administrator CRUD Methods
	public List<Administrator> getAllAdministrators() {
		return dao.getAllAdministrators();
	}

	public boolean insertAdministrator(Administrator admin) {
		return dao.insertUser(admin);
	}

	public boolean updateAdministrator(Administrator admin) {
		return dao.updateAdministrator(admin);
	}

	public boolean deleteAdministrator(String name) {
		return dao.deleteAdministrator(name);
	}

	public boolean checkAdminExists(String name) {
		return dao.checkAdminExists(name);
	}
	
	//worker CRUD Methods
//	public ArrayList<String> getWorkerCodes() {
//		return daoWorker.getWorkerCodes();
//	}
	
	public List<Worker> getAllWorker(){
		return daoWorker.getAllWorker();
	}

	public boolean checkUser(Administrator user) {
		return dao.checkUser(user);
	}
	
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
		return dao.updatePassword(adminName, newPassword);
	}
	
	// Windows Methods
	public void viewWindow() {
		StartWindow win = new StartWindow(this);
		win.setVisible(true);
	}
}