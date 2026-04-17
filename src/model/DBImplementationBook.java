package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Implementation of the BookDAO interface mapping interactions to a relational database.
 * 
 * @author Iker
 */
public class DBImplementationBook implements BookDAO {

	private Connection connection;
	private PreparedStatement statement;
	private ResourceBundle configFile;
	private String driverDB;
	private String urlDB;
	private String userDB;
	private String passwordDB;
	private String lastMessage;
	// SQL queries
	final String SQLSELECTALL ="SELECT id_client, cod_cruise, originCity, destinationCity, startDate, endDate, basePrice, finalPrice, room_number FROM book";
	final String SQLDELETE ="DELETE FROM book WHERE id_client = ? AND cod_cruise = ? AND startDate = ?";

	/**
	 * Initializes the DB implementation and fetches configuration properties.
	 */
	public DBImplementationBook() {
		this.configFile = ResourceBundle.getBundle("configClass");
		this.driverDB = this.configFile.getString("Driver");
		this.urlDB = this.configFile.getString("Conn");
		this.userDB = this.configFile.getString("DBUser");
		this.passwordDB = this.configFile.getString("DBPass");
	}

	private void openConnection() {
		try {
			connection = DriverManager.getConnection(urlDB, this.userDB, this.passwordDB);
		} catch (SQLException e) {
			System.out.println("Error opening DB: " + e.getMessage());
		}
	}

	public String getLastMessage() {
		return lastMessage;
	}

	/**
	 * Retrieves all bookings from the database.
	 *
	 * @return a List containing the retrieved booking objects.
	 */
	@Override
	public List<Book> getAllBookings() {
		List<Book> list = new ArrayList<>();
		openConnection();
		try {
			statement = connection.prepareStatement(SQLSELECTALL);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				list.add(new Book(
						rs.getString("id_client"),
						rs.getInt("cod_cruise"),
						rs.getString("originCity"),
						rs.getString("destinationCity"),
						rs.getTimestamp("startDate"),
						rs.getTimestamp("endDate"),
						rs.getDouble("basePrice"),
						rs.getDouble("finalPrice"),
						rs.getInt("room_number")
						));
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return list;
	}

	/**
	 * Creates a new booking using a stored procedure in the database.
	 *
	 * @param b the Book entity containing necessary data
	 * @return true if the creation is successful, false otherwise.
	 */
	@Override
	public boolean createBooking(Book b) {
		boolean ok = false;
		CruiseDAO daoCruise = new DBImplementationCruise();
	    Cruise c = daoCruise.getCruiseByCode(b.getCodCruise());

	    if (b.getRoomNumber() < 1 || b.getRoomNumber() > c.getNumRooms()) {
	        lastMessage = "Error: Room number exceeds available rooms.";
	        return false;
	    }
		openConnection();

		try {
			CallableStatement cs = connection.prepareCall("{CALL p_create_booking(?,?,?,?,?,?,?,?)}");
			cs.setString(1, b.getIdClient());
			cs.setInt(2, b.getCodCruise());
			cs.setString(3, b.getOriginCity());
			cs.setString(4, b.getDestinationCity());
			cs.setDate(5, new Date(b.getStartDate().getTime()));
			cs.setDate(6, new Date(b.getEndDate().getTime()));
			cs.setInt(7, b.getRoomNumber());
			cs.registerOutParameter(8, Types.VARCHAR);
			cs.execute();
			lastMessage = cs.getString(8);
			System.out.println("Procedure result: " + lastMessage);
			ok = (lastMessage != null && lastMessage.startsWith("Booking successfully"));
			cs.close();
			connection.close();
		} catch (SQLException e) {
			lastMessage = "Database error: " + e.getMessage();
			System.out.println("Error creating booking: " + e.getMessage());
		}
		return ok;
	}

	/**
	 * Deletes a booking matching the provided identifiers.
	 *
	 * @param idClient   ID of the client
	 * @param codCruise  Code of the cruise
	 * @param startDate  Start date of the booking
	 * @return true if successfully deleted, false otherwise
	 */
	@Override
	public boolean deleteBooking(String idClient, int codCruise, java.util.Date startDate) {
		boolean ok = false;
		openConnection();
		try {
			statement = connection.prepareStatement(SQLDELETE);
			statement.setString(1, idClient);
			statement.setInt(2, codCruise);
			statement.setDate(3, new Date(startDate.getTime()));
			ok = statement.executeUpdate() > 0;
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error deleting booking: " + e.getMessage());
		}
		return ok;
	}


	/**
	 * Updates an old booking by deleting it and creating a new one matching the updated details.
	 *
	 * @param oldBooking The original booking to be deleted
	 * @param newBooking The updated booking to insert
	 * @return true if the operation succeeds, false otherwise
	 */
	@Override
	public String updateBooking(Book oldBooking, Book newBooking) {
		String message = "";
		boolean deleted = deleteBooking(oldBooking.getIdClient(), oldBooking.getCodCruise(), oldBooking.getStartDate());

		if (!deleted) {
			lastMessage = "Error deleting old booking";
		}else {
			createBooking(newBooking);
			message = lastMessage;
		}
		return message;
	}

}