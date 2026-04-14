package model;

import java.util.List;

public interface ClientDAO {
	/**
	 * Retrieves a list of all clients by calling the getAllClient method of the
	 * ClientDAO implementation and returns it.
	 * 
	 * @return
	 */
	public List<Client> getAllClient();

	/**
	 * Retrieves a client by calling the getClientByCode method of the ClientDAO
	 * implementation with the provided client object and returns it.
	 * 
	 * @param client
	 * @return
	 */
	public Client getClientByCode(Client client);

	/**
	 * Deletes a client by calling the deleteClient method of the ClientDAO
	 * implementation with the provided client object and returns the result.
	 * 
	 * @param client
	 * @return
	 */
	public boolean deleteClient(Client client);

	/**
	 * Updates a client by calling the updateClientByCode method of the ClientDAO
	 * implementation with the provided client object and returns the result.
	 * 
	 * @param client
	 * @return
	 */
	public boolean updateClientByCode(Client client);

	/**
	 * Inserts a new client by calling the insertClient method of the ClientDAO
	 * implementation with the provided client object and returns the result.
	 * 
	 * @param client
	 * @return
	 */
	public boolean insertClient(Client client);

	/**
	 * Checks if a client exists by calling the checkClientInBook method of the
	 * ClientDAO implementation with the provided client id and returns the result.
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkClientInBook(String id);

}
