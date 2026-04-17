package model;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * JUnit 5 test class that verifies the CRUD operations of
 * DBImplementationAdministrator. It checks correct insertion,
 * retrieval, update and deletion of Administrator records, as well
 * as proper handling of invalid and non‑existent cases.
 *
 * @author Iker
 */
class TestDBImplementationAdministrator {

	private DBImplementationAdministrator dao;
	private Administrator created;

	@BeforeEach
	void setUp() throws Exception {
		dao = new DBImplementationAdministrator();
		created = null;
	}

	@AfterEach
	void tearDown() throws Exception {
		if (created != null) {
			dao.deleteAdministrator(created.getName());
		}
	}

	@Test
	void testInsertGetUpdateDeleteLifecycle() {

		String uniqueName = "TEST_ADMIN_" + System.currentTimeMillis();

		Administrator admin = new Administrator(uniqueName, "pass123");

		boolean inserted = dao.insertUser(admin);
		assertTrue(inserted, "insertUser should return true for a valid admin");

		List<Administrator> all = dao.getAllAdministrators();
		Administrator found = null;
		for (Administrator a : all) {
			if (uniqueName.equals(a.getName())) {
				found = a;
				break;
			}
		}
		assertNotNull(found, "Inserted admin should be findable by unique name");
		created = found;

		Administrator byName = dao.getAdministratorByName(uniqueName);
		assertNotNull(byName);
		assertEquals(found.getPassword(), byName.getPassword());

		String newPassword = "updatedPass123";
		byName.setPassword(newPassword);

		boolean updated = dao.updateAdministrator(byName);
		assertTrue(updated, "updateAdministrator should return true when update succeeds");

		Administrator updatedAdmin = dao.getAdministratorByName(uniqueName);
		assertNotNull(updatedAdmin);
		assertEquals(newPassword, updatedAdmin.getPassword());

		boolean deleted = dao.deleteAdministrator(uniqueName);
		assertTrue(deleted, "deleteAdministrator should return true when deletion succeeds");

		Administrator afterDelete = dao.getAdministratorByName(uniqueName);
		assertNull(afterDelete, "Admin should not be found after deletion");

		created = null;
	}

	@Test
	void testNonExistentOperations() {

		String nonExistentName = "NO_EXIST_999";

		// get should return null
		Administrator admin = dao.getAdministratorByName(nonExistentName);
		assertNull(admin);

		// delete should return false
		boolean deleted = dao.deleteAdministrator(nonExistentName);
		assertFalse(deleted);

		// checkAdminExists should return false
		boolean exists = dao.checkAdminExists(nonExistentName);
		assertFalse(exists);
	}

	@Test
	void testInsertNullAdminThrowsException() throws Exception {
		assertThrows(Exception.class, () -> {
			dao.insertUser(null);
		});
	}
}
