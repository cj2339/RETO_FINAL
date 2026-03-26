package controller;

import view.StartWindow;

import model.DBImplementation;
import model.DBImplementationCruiseDAO;

import java.util.ArrayList;
import java.util.List;

import model.Administrator;
import model.AdministratorDAO;
import model.Cruise;
import model.CruiseDAO;

public class LoginController {
	AdministratorDAO dao = new DBImplementation();
	CruiseDAO daoCruise = new DBImplementationCruiseDAO();
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
		return daoCruise.checkCruiseInWorker(id);
	}
	public boolean checkUser(Administrator user) {
		return dao.checkUser(user);
	}

	public ArrayList<String> getWorkerCodes() {
		return dao.getWorkerCodes();
	}

	public ArrayList<String> getClientCodes() {
		return dao.getClientCodes();
	}

	public boolean deleteWorker(String id) {
		return dao.deleteWorker(id);
	}

	public boolean deleteClient(String id) {
		return dao.deleteClient(id);
	}

	public boolean deleteBook(String codCruise, String idClient) {
		return dao.deleteBook(codCruise, idClient);
	}

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