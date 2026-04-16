import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Cruise;
import model.DBImplementationCruise;
import model.DBImplementationWorker;
import model.TypeWorker;
import model.Worker;

class TestDBImplementationWorker {

	private DBImplementationWorker dao;
	private Worker created;

	@BeforeEach	// runs before each test method
	void setUp() throws Exception {
		dao = new DBImplementationWorker();
		created = null;
	}

	@AfterEach // runs after each test method
	void tearDown() throws Exception {
		// Ensure cleanup: if a worker was created during a test, attempt to delete it
		if (created != null) {
			dao.deleteWorker(String.valueOf(created.getIdWorker()));
		}
	}

	@Test
	void testWorkerLifecycleAndAssertTypes() {
		String testId = "99999999Z";
		String testPhone = "600111222";
		String testEmail = "junit@test.com";

		Cruise cruise = new Cruise();
		cruise.setCodCruise(1); 


		Worker worker = new Worker(testId, TypeWorker.CAPTAIN, "TestName", "TestSurname", 
				new java.util.Date(), testPhone, testEmail, 30, 
				true, false, cruise);

		//Check that the worker does not already exist
		assertFalse(dao.idWorkerExists(testId), "The ID should not exist before the test starts");

		//Insert the worker and verify that the operation returns true
		boolean inserted = dao.insertWorker(worker);
		assertTrue(inserted, "insertWorker should return true for a valid worker");

		//Search the worker on the workers list
		List<Worker> allWorkers = dao.getAllWorker();
		assertNotNull(allWorkers, "The worker list should not be null");

		// Look for the inserted worker
		Worker found = null;
		for (Worker w : allWorkers) {
			if (w.getIdWorker().equals(testId)) {
				found = w;
				break;
			}
		}
		//Verify that the worker was found in the list
		assertNotNull(found, "The inserted worker should be findable in the database");

		//Compare the name with the original one
		assertEquals("TestName", found.getName(), "The obtained name must match the original name");

		//Update a field and verify 
		found.setName("UpdatedName");
		dao.updateWorker(found);

		//Verify the update by checking the list again
		List<Worker> updatedList = dao.getAllWorker();
		String nameAfterUpdate = "";
		for(Worker w : updatedList) {
			if(w.getIdWorker().equals(testId)) {
				nameAfterUpdate = w.getName();
			}
		}
		assertEquals("UpdatedName", nameAfterUpdate, "The name should be updated correctly in the DB");

		//Verify that the method throws an exception when passing a null object
		assertThrows(NullPointerException.class, () -> {
			dao.insertWorker(null);
		}, "Inserting a null worker should throw a NullPointerException");

		//Delete the test worker to leave the original database
		boolean deleted = dao.deleteWorker(testId);
		assertTrue(deleted, "deleteWorker should return true when the operation succeeds");

		//The worker should no longer exist
		assertFalse(dao.idWorkerExists(testId), "The worker should not exist after deletion");
	}

	@Test
	void testNonExistentWorkerOperations() {
		//Use an ID that does not exist in the database
		String nonExistentId = "NOT_EXIST";


		//Checking existence for a fake ID should return false
		assertFalse(dao.idWorkerExists(nonExistentId), "idWorkerExists should be false for unknown ID");
		assertFalse(dao.phoneWorkerExists("000000"), "phoneWorkerExists should be false for unknown phone");
		assertFalse(dao.emailWorkerExists("fake@email.com"), "emailWorkerExists should be false for unknown email");

		//Deleting an ID that doesn't exist should return false
		boolean isDeleted = dao.deleteWorker(nonExistentId);
		assertFalse(isDeleted, "deleteWorker should return false when the ID is not in the database");

		//Updating a non-existent worker should return false
		Worker fakeWorker = new Worker();
		fakeWorker.setIdWorker(nonExistentId);
		fakeWorker.setName("Fake");
		assertFalse(dao.updateWorker(fakeWorker), "updateWorker should return false if the record doesn't exist");
	}

}
