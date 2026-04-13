package model;

import java.util.List;

public interface ClientDAO {
	
	public List<Client> getAllClient();//returns a list of all clients
	public Client getClientByCode(Client client);//retrieves a client by their unique identifier (code)
	public boolean deleteClient(Client client);	//deletes a client from the database
	public boolean updateClientByCode(Client client);//updates a client's information in the database based on their unique identifier (code)
	public boolean insertClient(Client client);	//inserts a new client into the database
	public boolean checkClientInBook(String id);//checks if a client is associated with any booking in the database by their unique identifier (id)

}
