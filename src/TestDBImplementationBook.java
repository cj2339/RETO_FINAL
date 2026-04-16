import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Book;
import model.DBImplementationBook;

class TestDBImplementationBook {

	private DBImplementationBook dao;
	private Book created;

	@BeforeEach
	void setUp() throws Exception {
		dao = new DBImplementationBook();
		created = null;
	}

	@AfterEach
	void tearDown() throws Exception {
		if (created != null) {
			dao.deleteBooking(created.getIdClient(), created.getCodCruise(), created.getStartDate());
		}
	}

	@Test
	void testBookingLifecycle() {
		String idClient = "TESTCLIENT";
		int codCruise = 1;
		String origin = "Bilbao";
		String destination = "Lisbon";
		Date start = new Date(System.currentTimeMillis() + 86400000L * 20); // +20 días
		Date end = new Date(System.currentTimeMillis() + 86400000L * 25);   // +25 días
		Book booking = new Book(idClient, codCruise, origin, destination, start, end, 0, 0, 101);

		// Insert
		boolean inserted = dao.createBooking(booking);
		created = booking;
		assertTrue(inserted, "createBooking should return true for a valid booking");

		// Check list
		List<Book> all = dao.getAllBookings();
		assertNotNull(all, "List should not be null");

		Book found = null;
		for (Book b : all) {
			if (b.getIdClient().equals(idClient) && b.getCodCruise() == codCruise) {
				found = b;
				break;
			}
		}
		assertNotNull(found, "Inserted booking should be found");

		// Update
		Book updated = new Book(idClient, codCruise, origin, "Barcelona", start, end, 0, 0, 101);
		String updateMsg = dao.updateBooking(booking, updated);
		assertTrue(updateMsg.contains("Booking"), "Update should return a success message");

		// Check update
		List<Book> updatedList = dao.getAllBookings();
		String destinationAfterUpdate = "";
		for (Book b : updatedList) {
			if (b.getIdClient().equals(idClient)) {
				destinationAfterUpdate = b.getDestinationCity();
			}
		}
		assertEquals("Barcelona", destinationAfterUpdate, "Destination should be updated");

		// Delete
		boolean deleted = dao.deleteBooking(idClient, codCruise, start);
		assertTrue(deleted, "deleteBooking should return true");

		// Check deletion
		List<Book> afterDelete = dao.getAllBookings();
		boolean exists = false;
		for (Book b : afterDelete) {
			if (b.getIdClient().equals(idClient)) {
				exists = true;
			}
		}
		assertFalse(exists, "Booking should not exist after deletion");
	}

	@Test
	void testInvalidOperations() {

		// Insert null
		assertThrows(NullPointerException.class, () -> {
			dao.createBooking(null);
		});

		// Update with nulls
		Book fake = new Book();
		fake.setIdClient("FAKE");
		fake.setCodCruise(999);
		fake.setStartDate(new Date());

		String msg = dao.updateBooking(fake, fake);
		assertTrue(msg.contains("Error"), "Updating non-existent booking should return error message");

		// Delete non-existent
		boolean deleted = dao.deleteBooking("NOPE", 999, new Date());
		assertFalse(deleted, "Deleting non-existent booking should return false");
	}
}