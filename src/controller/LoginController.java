package controller;

import java.util.Date;
import java.util.List;

import model.Administrator;
import model.AdministratorDAO;
import model.Book;
import model.BookDAO;
import model.ClientDAO;
import model.Cruise;
import model.CruiseDAO;
import model.DBImplementationClient;
import model.DBImplementationAdministrator;
import model.DBImplementationBook;
import model.DBImplementationCruise;
import model.DBImplementationWorker;
import model.Worker;
import model.WorkerDAO;
import view.StartWindow;
import model.Client;

/**
 * Controller class managing the interactions between the View and the Model (DAO layer)
 * for the cruise booking application. Consolidates CRUD logic for cruises, administrators, 
 * clients, workers, and bookings.
 * 
 * @author Santiago
 * @author Iker
 * @author Etna
 * @author Aritz
 */
public class LoginController {

	AdministratorDAO daoAdministrator = new DBImplementationAdministrator();
	CruiseDAO daoCruise = new DBImplementationCruise();
	WorkerDAO daoWorker = new DBImplementationWorker();
	ClientDAO daoClient = new DBImplementationClient();
	BookDAO daoBooking = new DBImplementationBook();
	private String loggedInAdminName;

	// Cruise CRUD Methods
	/**
	 * Retrieves a list of all cruises by calling the getAllCruise method of the
	 * CruiseDAO implementation and returns it.
	 * 
	 * @return 
	 */
	public List<Cruise> getAllCruise() {
		return daoCruise.getAllCruise();
	}

	/**
	 * Deletes a cruise by calling the deleteCruise method of the CruiseDAO
	 * implementation with the provided id and returns the result.
	 * 
	 * @param id
	 * @return 
	 */
	public boolean deleteCruise(String id) {
		return daoCruise.deleteCruise(id);
	}

	/**
	 * Updates a cruise by calling the updateCruiseByCode method of the CruiseDAO
	 * implementation with the provided cruise object and returns the result.
	 * 
	 * @param cruise
	 * @return
	 */
	public boolean updateCruiseByCode(Cruise cruise) {
		return daoCruise.updateCruiseByCode(cruise);
	}

	/**
	 * Retrieves a cruise by calling the getCruiseByCode method of the CruiseDAO
	 * implementation with the provided id and returns it.
	 * 
	 * @param id
	 * @return
	 */
	public Cruise getCruiseByCode(String id) {
		return daoCruise.getCruiseByCode(id);
	}

	/**
	 * Inserts a new cruise by calling the insertCruise method of the CruiseDAO
	 * implementation with the provided cruise object and returns the result.
	 * 
	 * @param cruise
	 * @return
	 */
	public boolean insertCruise(Cruise cruise) {
		return daoCruise.insertCruise(cruise);
	}

	/**
	 * Checks if a cruise is associated with any worker by calling the
	 * checkCruiseInWorker method of the CruiseDAO implementation with the provided
	 * id and returns the result.
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkCruiseInWorker(String id) {
		return daoCruise.checkCruiseInWorker(id);
	}

	/**
	 * Checks if a cruise is associated with any booking by calling the
	 * checkCruiseInBook method of the CruiseDAO implementation with the provided id
	 * and returns the result.
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkCruiseInBook(String id) {
		return daoCruise.checkCruiseInBook(id);
	}

	public int getNextCruiseCode() {
		return daoCruise.getNextCruiseCode();
	}

	// Administrator CRUD Methods
	/**
	 * Retrieves a list of all administrators by calling the getAllAdministrators
	 * method of the AdministratorDAO implementation and returns it.
	 * 
	 * @return
	 */
	public List<Administrator> getAllAdministrators() {
		return daoAdministrator.getAllAdministrators();
	}

	/**
	 * Inserts a new administrator by calling the insertUser method of the
	 * AdministratorDAO implementation with the provided administrator object and
	 * returns the result.
	 * 
	 * @param admin
	 * @return
	 */
	public boolean insertAdministrator(Administrator admin) {
		return daoAdministrator.insertUser(admin);
	}

	/**
	 * Updates an administrator by calling the updateAdministrator method of the
	 * AdministratorDAO implementation with the provided administrator object and
	 * returns the result.
	 * 
	 * @param admin
	 * @return
	 */
	public boolean updateAdministrator(String oldName, String newName, String newPass) {
		return daoAdministrator.updateAdministrator(oldName, newName, newPass);
	}

	/**
	 * Deletes an administrator by calling the deleteAdministrator method of the
	 * AdministratorDAO implementation with the provided name and returns the
	 * result.
	 * 
	 * @param name
	 * @return
	 */
	public boolean deleteAdministrator(String name) {
		return daoAdministrator.deleteAdministrator(name);
	}

	/**
	 * Checks if an administrator exists by calling the checkAdminExists method of
	 * the AdministratorDAO implementation with the provided name and returns the
	 * result.
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkAdminExists(String name) {
		return daoAdministrator.checkAdminExists(name);
	}

	// Client CRUD Methods

	/**
	 * Retrieves a list of all clients by calling the getAllClient method of the
	 * ClientDAO implementation and returns it.
	 * 
	 * @return
	 */
	public List<Client> getAllClient() {
		return daoClient.getAllClient();
	}

	/**
	 * Deletes a client by calling the deleteClient method of the ClientDAO
	 * implementation with the provided client object and returns the result.
	 * 
	 * @param client
	 * @return
	 */
	public boolean deleteClient(Client client) {
		return daoClient.deleteClient(client);
	}

	/**
	 * Retrieves a client by calling the getClientByCode method of the ClientDAO
	 * implementation with the provided client object and returns it.
	 * 
	 * @param client
	 * @return
	 */
	public Client getClientByCode(Client client) {
		return daoClient.getClientByCode(client);
	}

	/**
	 * Updates a client by calling the updateClientByCode method of the ClientDAO
	 * implementation with the provided client object and returns the result.
	 * 
	 * @param client
	 * @return
	 */
	public boolean updateClientByCode(Client client) {
		return daoClient.updateClientByCode(client);
	}

	/**
	 * Inserts a new client by calling the insertClient method of the ClientDAO
	 * implementation with the provided client object and returns the result.
	 * 
	 * @param client
	 * @return
	 */
	public boolean insertClient(Client client) {
		return daoClient.insertClient(client);
	}

	/**
	 * Checks if a client is associated with any booking by calling the
	 * checkClientInBook method of the ClientDAO implementation with the provided id
	 * and returns the result.
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkClientInBook(String id) {
		return daoClient.checkClientInBook(id);
	}

	// worker CRUD Methods
	//	public ArrayList<String> getWorkerCodes() {
	//		return daoWorker.getWorkerCodes();
	//	}

	/**
	 * Retrieves a list of all workers by calling the getAllWorker method of the
	 * WorkerDAO implementation and returns it.
	 * 
	 * @return
	 */
	public List<Worker> getAllWorker() {
		return daoWorker.getAllWorker();
	}

	/**
	 * Checks if an administrator's credentials are valid.
	 * 
	 * @param user The administrator object containing name and password.
	 * @return true if credentials are valid, false otherwise.
	 */
	public boolean checkUser(Administrator user) {
		return daoAdministrator.checkUser(user);
	}

	/**
	 * Deletes a worker from the database using their ID.
	 * 
	 * @param id The ID of the worker to delete.
	 * @return true if successfully deleted, false otherwise.
	 */
	public boolean deleteWorker(String id) {
		return daoWorker.deleteWorker(id);
	}

	/**
	 * Updates the details of an existing worker.
	 * 
	 * @param worker The worker object holding the updated data.
	 * @return true if updated successfully, false otherwise.
	 */
	public boolean updateWorker(Worker worker) {
		return daoWorker.updateWorker(worker);
	}

	/**
	 * Inserts a newly created worker into the system.
	 * 
	 * @param worker The worker to be inserted.
	 * @return true if successfully inserted, false otherwise.
	 */
	public boolean insertWorker(Worker worker) {
		return daoWorker.insertWorker(worker);
	}

	public boolean idWorkerExists(String id) {
		return daoWorker.idWorkerExists(id);
	}

	public boolean phoneWorkerExists(String phone) {
		return daoWorker.phoneWorkerExists(phone);
	}

	public boolean phoneWorkerExistsExclude(String phone, String id) {
		return daoWorker.phoneWorkerExistsExclude(phone, id);
	}

	/**
	 * Gets the name of the administrator currently logged into the system.
	 * 
	 * @return The name of the logged-in administrator.
	 */
	public String getLoggedInAdminName() {
		return loggedInAdminName;
	}

	/**
	 * Sets the name of the logged-in administrator by assigning the provided name
	 * to the loggedInAdminName variable.
	 * 
	 * @param loggedInAdminName
	 */
	public void setLoggedInAdminName(String loggedInAdminName) {
		this.loggedInAdminName = loggedInAdminName;
	}

	/**
	 * Updates the password of an administrator by calling the updatePassword method
	 * of the AdministratorDAO implementation with the provided administrator name
	 * and new password and returns the result.
	 * 
	 * @param adminName
	 * @param newPassword
	 * @return
	 */
	public boolean updatePassword(String adminName, String newPassword) {
		return daoAdministrator.updatePassword(adminName, newPassword);
	}

	// Book Methods

	/**
	 * Retrieves all bookings existing in the database.
	 * 
	 * @return A list of all bookings.
	 */
	public List<Book> getAllBookings() {
		return daoBooking.getAllBookings();
	}

	/**
	 * Creates a new booking entry.
	 * 
	 * @param b The booking object to insert.
	 * @return true if booking creation was successful, false otherwise.
	 */
	public String createBooking(Book b) {
		Cruise c = daoCruise.getCruiseByCode(b.getCodCruise());
		if (b.getRoomNumber() < 1 || b.getRoomNumber() > c.getNumRooms()) {
			return "Error: Room number exceeds the number of rooms for this cruise.";
		}
		daoBooking.createBooking(b);
		return ((DBImplementationBook) daoBooking).getLastMessage();
	}

	/**
	 * Deletes a booking given its client ID, cruise code, and start date.
	 * 
	 * @param idClient  The client's ID.
	 * @param codCruise The code of the cruise.
	 * @param startDate The start date of the cruise booking.
	 * @return true if deleted properly, false otherwise.
	 */
	public boolean deleteBooking(String idClient, int codCruise, Date startDate) {
		return daoBooking.deleteBooking(idClient, codCruise, startDate);
	}

	/**
	 * Replaces an existing booking with a new updated booking.
	 * 
	 * @param oldB The former booking.
	 * @param newB The newly updated booking.
	 * @return true if successfully updated, false otherwise.
	 */
	public String updateBooking(Book oldB, Book newB) {
		Cruise c = daoCruise.getCruiseByCode(newB.getCodCruise());
		if (newB.getRoomNumber() < 1 || newB.getRoomNumber() > c.getNumRooms()) {
			return "Error: Room number exceeds the number of rooms for this cruise.";
		}
		return daoBooking.updateBooking(oldB, newB);
	}

	// Windows Methods
	/**
	 * Creates a new instance of the StartWindow class, passing the current instance
	 * of LoginController as a parameter, and sets the window to be visible.
	 */
	public void viewWindow() {
		StartWindow win = new StartWindow(this);
		win.setVisible(true);
	}
}