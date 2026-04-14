package model;

import java.util.Date;
import java.util.List;

public interface BookDAO {
	boolean createBooking(Book booking);
	boolean deleteBooking(String idClient, int codCruise, Date startDate);
	List<Book> getAllBookings();
	boolean updateBooking(Book oldBooking, Book newBooking); // opción A
}