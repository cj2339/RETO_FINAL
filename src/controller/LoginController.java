package controller;

import view.StartWindow;

import model.DBImplementation;
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
	
}
