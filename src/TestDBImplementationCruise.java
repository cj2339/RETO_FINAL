import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Cruise;
import model.DBImplementationCruise;
import model.TypeCruise;

class TestDBImplementationCruise {

	private DBImplementationCruise dao;
	private Cruise created;

	@BeforeEach // runs before each test method
	void setUp() throws Exception {
		dao = new DBImplementationCruise();
		created = null;
	}

	@AfterEach// runs after each test method
	void tearDown() throws Exception {
		// Ensure cleanup: if a cruise was created during a test, attempt to delete it
		if (created != null) {
			dao.deleteCruise(String.valueOf(created.getCodCruise()));
		}
	}

	@Test
	void testInsertGetUpdateDeleteLifecycle() {// This test covers the full lifecycle of a cruise record(insert, get, update, delete)
		// Create a cruise with a unique name so tests are repeatable
		String uniqueName = "TEST_CRUISE_" + System.currentTimeMillis();// Using timestamp to ensure uniqueness across test runs
		Cruise cruise = new Cruise();
		cruise.setNameCruise(uniqueName);
		cruise.setTypeCruise(TypeCruise.FAMILY);
		cruise.setNumRooms(10);
		cruise.setCapacityMax(100);

		// Insert should return true
		boolean inserted = dao.insertCruise(cruise);
		assertTrue(inserted, "insertCruise should return true for a valid cruise");

		// After insert, we need to find the inserted cruise. The implementation does not return generated id, so retrieve all and match by unique name
		List<Cruise> all = dao.getAllCruise();
		Cruise found = null;
		for (Cruise c : all) {
			if (uniqueName.equals(c.getNameCruise())) {
				found = c;
				break;
			}
		}
		assertNotNull(found, "Inserted cruise should be findable by unique name");
		created = found; // save for cleanup

		// getCruiseByCode should return the same cruise
		Cruise byCode = dao.getCruiseByCode(String.valueOf(found.getCodCruise()));
		assertNotNull(byCode);
		assertEquals(found.getNameCruise(), byCode.getNameCruise());

		// Update some fields
		byCode.setNameCruise(found.getNameCruise() + "_U");
		byCode.setNumRooms(found.getNumRooms() + 5);
		byCode.setCapacityMax(found.getCapacityMax() + 20);
		boolean updated = dao.updateCruiseByCode(byCode);
		assertTrue(updated, "updateCruiseByCode should return true when update succeeds");

		// Verify update
		Cruise updatedCruise = dao.getCruiseByCode(String.valueOf(found.getCodCruise()));
		assertNotNull(updatedCruise);
		assertEquals(byCode.getNameCruise(), updatedCruise.getNameCruise());
		assertEquals(byCode.getNumRooms(), updatedCruise.getNumRooms());
		assertEquals(byCode.getCapacityMax(), updatedCruise.getCapacityMax());

		// Delete and verify deletion
		boolean deleted = dao.deleteCruise(String.valueOf(found.getCodCruise()));
		assertTrue(deleted, "deleteCruise should return true when deletion succeeds");

		Cruise afterDelete = dao.getCruiseByCode(String.valueOf(found.getCodCruise()));
		assertNull(afterDelete, "Cruise should not be found after deletion");

		// Mark as cleaned up
		created = null;
	}

	@Test
	void testNonExistentOperations() {
		// Use a likely-nonexistent id (negative or very large)
		String nonExistentId = "-999999";

		// get should return null
		assertNull(dao.getCruiseByCode(nonExistentId));

		// delete should return false
		assertFalse(dao.deleteCruise(nonExistentId));

		// check in worker/book should return false (assuming DB has no entries for this id)
		assertFalse(dao.checkCruiseInWorker(nonExistentId));
		assertFalse(dao.checkCruiseInBook(nonExistentId));
	}

	@Test
	void testInsertNullCruiseThrowsException() throws Exception {
		// Test that inserting a null cruise throws an exception
		assertThrows(Exception.class, () -> {
			dao.insertCruise(null);
		});
	}

}