package model;

import java.util.Date;
import java.util.List;

/**
 * Data Access Object interface for managing Book entities.
 */
public interface BookDAO {
	
	/**
	 * Creates a new booking in the underlying database.
	 *
	 * @param booking The booking object to persist.
	 * @return true if successful, false otherwise.
	 */
	boolean createBooking(Book booking);

	/**
	 * Deletes an existing booking based on multiple primary keys.
	 *
	 * @param idClient   The client ID associated with the booking.
	 * @param codCruise  The cruise code.
	 * @param startDate  The start date of the booking.
	 * @return true if deletion is successful, false otherwise.
	 */
	boolean deleteBooking(String idClient, int codCruise, Date startDate);

	/**
	 * Retrieves a list of all current bookings in the database.
	 *
	 * @return a List containing Book objects.
	 */
	List<Book> getAllBookings();

	/**
	 * Updates an existing booking by replacing it with a new booking structure.
	 *
	 * @param oldBooking The current booking data to be updated.
	 * @param newBooking The updated booking data.
	 * @return true if update is successful, false otherwise.
	 */
	boolean updateBooking(Book oldBooking, Book newBooking);
}