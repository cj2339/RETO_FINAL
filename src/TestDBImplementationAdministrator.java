import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Administrator;
import model.DBImplementationAdministrator;

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

		// Nombre único para evitar colisiones
		String uniqueName = "TEST_ADMIN_" + System.currentTimeMillis();

		Administrator admin = new Administrator(uniqueName, "pass123");

		// INSERT
		boolean inserted = dao.insertUser(admin);
		assertTrue(inserted, "insertUser should return true for a valid admin");

		// LIST
		List<Administrator> all = dao.getAllAdministrators();
		Administrator found = null;
		for (Administrator a : all) {
			if (uniqueName.equals(a.getName())) {
				found = a;
				break;
			}
		}
		assertNotNull(found, "Inserted admin should be findable by unique name");
		created = found; // para cleanup

		// GET BY NAME
		Administrator byName = dao.getAdministratorByName(uniqueName);
		assertNotNull(byName);
		assertEquals(found.getPassword(), byName.getPassword());

		// UPDATE PASSWORD
		String newPassword = "updatedPass123";
		byName.setPassword(newPassword);

		boolean updated = dao.updateAdministrator(byName);
		assertTrue(updated, "updateAdministrator should return true when update succeeds");

		// VERIFY UPDATE
		Administrator updatedAdmin = dao.getAdministratorByName(uniqueName);
		assertNotNull(updatedAdmin);
		assertEquals(newPassword, updatedAdmin.getPassword());

		// DELETE
		boolean deleted = dao.deleteAdministrator(uniqueName);
		assertTrue(deleted, "deleteAdministrator should return true when deletion succeeds");

		Administrator afterDelete = dao.getAdministratorByName(uniqueName);
		assertNull(afterDelete, "Admin should not be found after deletion");

		created = null; // ya está borrado
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
