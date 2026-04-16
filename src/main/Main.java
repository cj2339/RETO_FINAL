package main;
import controller.LoginController;

/**
 * Entry point of the application. Initializes the LoginController and launches
 * the login window to start the program workflow.
 * 
 * @author Santiago
 * @author Iker
 * @author Etna
 * @author Aritz
 */
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