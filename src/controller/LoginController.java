package controller;

import view.StartWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import model.DBImplementation;
import model.User;
import model.UserDAO;

public class LoginController {
	UserDAO dao = new DBImplementation();

	public void viewWindow() {
		StartWindow win=new StartWindow(this);
		win.setVisible(true);
	}

	public boolean checkUser(User user) {
		return dao.checkUser(user);
	}
	
}
