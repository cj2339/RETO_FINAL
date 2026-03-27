package model;

import java.util.List;

public interface ClientDAO {
	
	public List<Client> getAllClient();
	public Client getClientByCode(Client client);
	public boolean deleteClient(Client client);
	public boolean updateClientByCode(Client client);
	public boolean insertClient(Client client);

}
