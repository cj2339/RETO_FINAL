package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Book;
import model.DBImplementationBook;

/**
 * JUnit tests for DBImplementationBook, covering booking creation,
 * retrieval, update, deletion and invalid operations.
 * 
 * @author Iker
 */
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

        // Datos reales de la BD
        String idClient = "11111111A";
        int codCruise = 1;
        int room = 1;

        String origin = "Bilbao";
        String destination = "Lisbon";

        Date start = new Date(System.currentTimeMillis() + 86400000L * 20);
        Date end = new Date(System.currentTimeMillis() + 86400000L * 25);

        Book booking = new Book(idClient, codCruise, origin, destination, start, end, 0, 0, room);

        // INSERT
        boolean inserted = dao.createBooking(booking);
        System.out.println("Mensaje del procedimiento (INSERT): " + dao.getLastMessage());

        created = booking;
        assertTrue(inserted, "createBooking should return true for a valid booking");

        // LIST
        List<Book> all = dao.getAllBookings();
        assertNotNull(all);

        Book found = null;
        for (Book b : all) {
            boolean sameClient = b.getIdClient().equals(idClient);
            boolean sameCruise = b.getCodCruise() == codCruise;
            boolean sameDate = Math.abs(b.getStartDate().getTime() - start.getTime()) < 2000;

            if (sameClient && sameCruise && sameDate) {
                found = b;
                break;
            }
        }
        assertNotNull(found, "Inserted booking should be found");

        // UPDATE
        Book updated = new Book(idClient, codCruise, origin, "Barcelona", start, end, 0, 0, room);
        String updateMsg = dao.updateBooking(booking, updated);
        System.out.println("Mensaje del procedimiento (UPDATE): " + dao.getLastMessage());

        assertFalse(updateMsg.isEmpty(), "Update should return a non-empty message");

        // CHECK UPDATE
        List<Book> updatedList = dao.getAllBookings();
        String destinationAfterUpdate = "";
        for (Book b : updatedList) {
            boolean sameClient = b.getIdClient().equals(idClient);
            boolean sameDate = Math.abs(b.getStartDate().getTime() - start.getTime()) < 2000;

            if (sameClient && sameDate) {
                destinationAfterUpdate = b.getDestinationCity();
            }
        }
        assertEquals("Barcelona", destinationAfterUpdate, "Destination should be updated");

        // DELETE
        boolean deleted = dao.deleteBooking(idClient, codCruise, start);
        System.out.println("Mensaje del procedimiento (DELETE): " + dao.getLastMessage());

        assertTrue(deleted, "deleteBooking should return true");

        // CHECK DELETE
        List<Book> afterDelete = dao.getAllBookings();
        boolean exists = false;
        for (Book b : afterDelete) {
            boolean sameClient = b.getIdClient().equals(idClient);
            boolean sameDate = Math.abs(b.getStartDate().getTime() - start.getTime()) < 2000;

            if (sameClient && sameDate) {
                exists = true;
            }
        }
        assertFalse(exists, "Booking should not exist after deletion");
    }

    @Test
    void testInvalidOperations() {

        // Insert null
        assertThrows(Exception.class, () -> {
            dao.createBooking(null);
        });

        // Update non-existent
        Book fake = new Book();
        fake.setIdClient("FAKE");
        fake.setCodCruise(999);
        fake.setStartDate(new Date());

        String msg = dao.updateBooking(fake, fake);
        System.out.println("Mensaje del procedimiento (UPDATE inválido): " + dao.getLastMessage());
        assertNotNull(msg);

        // Delete non-existent
        boolean deleted = dao.deleteBooking("NOPE", 999, new Date());
        System.out.println("Mensaje del procedimiento (DELETE inválido): " + dao.getLastMessage());
        assertFalse(deleted);
    }
}