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

//	public void visualizarPantalla() {
//		StartWindow ven = new StartWindow(this);
//		ven.setVisible(true);	
//	}
	public boolean checkUser(User user) {
		return dao.checkUser(user);
	}
	
}
