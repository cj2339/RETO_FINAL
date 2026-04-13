package model;

import java.util.List;

public interface CruiseDAO {
	
	public List<Cruise> getAllCruise();//returns a list of all cruises
	public Cruise getCruiseByCode(String id);//retrieves a cruise by its unique identifier (code)
	public boolean deleteCruise(String id);//deletes a cruise from the database by its unique identifier (id)
	public boolean updateCruiseByCode(Cruise cruise);//updates a cruise's information in the database based on its unique identifier (code)
	public boolean insertCruise(Cruise cruise);//inserts a new cruise into the database
	public boolean checkCruiseInWorker(String id);//checks if a cruise is associated with any worker in the database by its unique identifier (id)
	public boolean checkCruiseInBook(String id);//checks if a cruise is associated with any booking in the database by its unique identifier (id)
}
