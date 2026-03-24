package controller;

import view.StartWindow;

import model.DBImplementation;

import java.util.ArrayList;

import model.Administrator;
import model.AdministratorDAO;

public class LoginController {
	AdministratorDAO dao = new DBImplementation();

	public void viewWindow() {
		StartWindow win=new StartWindow(this);
		win.setVisible(true);
	}

	public boolean checkUser(Administrator user) {
		return dao.checkUser(user);
	} 
	
	public ArrayList<String> getCruiseCodes() {
	    return dao.getCruiseCodes();
	}

	public ArrayList<String> getWorkerCodes() {
	    return dao.getWorkerCodes();
	}

	public ArrayList<String> getClientCodes() {
		return dao.getClientCodes();
	}
	
	
}
