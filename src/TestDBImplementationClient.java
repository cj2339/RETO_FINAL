

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Client;
import model.DBImplementationClient;

class TestDBImplementationClient {

    private DBImplementationClient dao;
    private Client created;

    @BeforeEach // runs before each test method
    void setUp() throws Exception {
        dao = new DBImplementationClient();
        created = null;
    }

    @AfterEach // runs after each test method
    void tearDown() throws Exception {
        // Ensure cleanup: if a client was created during a test, attempt to delete it
        if (created != null) {
            dao.deleteClient(created);
        }
    }

    @Test
    void testInsertGetUpdateDeleteLifecycle() {
        // Create a client with a unique ID so tests are repeatable
        String uniqueId = "TEST_CLIENT_" + System.currentTimeMillis();

        Client client = new Client();
        client.setIdClient(uniqueId);
        client.setNameClient("TestName");
        client.setSurnameClient("TestSurname");
        client.setAgeClient(30);
        client.setPhoneClient(600123456);
        client.setEmailClient("test@example.com");

        // Insert should return true
        boolean inserted = dao.insertClient(client);
        assertTrue(inserted, "insertClient should return true for a valid client");

        // After insert, retrieve all and match by unique ID
        List<Client> all = dao.getAllClient();
        Client found = null;
        for (Client c : all) {
            if (uniqueId.equals(c.getIdClient())) {
                found = c;
                break;
            }
        }
        assertNotNull(found, "Inserted client should be findable by unique ID");
        created = found; // save for cleanup

        // getClientByCode should return the same client
        Client query = new Client();
        query.setIdClient(uniqueId);

        Client byCode = dao.getClientByCode(query);
        assertNotNull(byCode);
        assertEquals(found.getNameClient(), byCode.getNameClient());

        // Update some fields
        byCode.setNameClient(found.getNameClient() + "_U");
        byCode.setSurnameClient(found.getSurnameClient() + "_U");
        byCode.setAgeClient(found.getAgeClient() + 5);
        byCode.setPhoneClient(found.getPhoneClient() + 100);
        byCode.setEmailClient("updated@example.com");

        boolean updated = dao.updateClientByCode(byCode);
        assertTrue(updated, "updateClientByCode should return true when update succeeds");

        // Verify update
        Client updatedClient = dao.getClientByCode(query);
        assertNotNull(updatedClient);
        assertEquals(byCode.getNameClient(), updatedClient.getNameClient());
        assertEquals(byCode.getSurnameClient(), updatedClient.getSurnameClient());
        assertEquals(byCode.getAgeClient(), updatedClient.getAgeClient());
        assertEquals(byCode.getPhoneClient(), updatedClient.getPhoneClient());
        assertEquals(byCode.getEmailClient(), updatedClient.getEmailClient());

        // Delete and verify deletion
        boolean deleted = dao.deleteClient(query);
        assertTrue(deleted, "deleteClient should return true when deletion succeeds");

        Client afterDelete = dao.getClientByCode(query);
        assertNull(afterDelete, "Client should not be found after deletion");

        // Mark as cleaned up
        created = null;
    }

    @Test
    void testNonExistentOperations() {
        // Use a likely-nonexistent id
        String nonExistentId = "NO_EXIST_999";

        Client fake = new Client();
        fake.setIdClient(nonExistentId);

        // get should return null
        assertNull(dao.getClientByCode(fake));

        // delete should return false
        assertFalse(dao.deleteClient(fake));

        // check in book should return false
        assertFalse(dao.checkClientInBook(nonExistentId));
    }

    @Test
    void testInsertNullClientThrowsException() throws Exception {
        // Test that inserting a null client throws an exception
        assertThrows(Exception.class, () -> {
            dao.insertClient(null);
        });
    }

}
