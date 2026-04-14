package main;
import controller.LoginController;

public class Main {
	/**
	 * The main method serves as the entry point of the application. It creates an
	 * instance of the LoginController and calls the viewWindow method to display
	 * the login window.
	 * 
	 * @param args Command-line arguments (not used in this application).
	 */
	public static void main(String[] args) {
		LoginController cont=new LoginController();
		cont.viewWindow();
	}
}