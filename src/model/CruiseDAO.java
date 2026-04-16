package model;

import java.util.List;

/**
 * Data Access Object interface defining operations for Cruise management.
 * 
 * @author Aritz
 */
public interface CruiseDAO {

	/**
	 * Retrieves a list of all cruises by calling the getAllCruise method of the
	 * 
	 * @return
	 */
	public List<Cruise> getAllCruise();

	/**
	 * Retrieves a cruise by calling the getCruiseByCode method of the CruiseDAO
	 * 
	 * @param id
	 * @return
	 */
	public Cruise getCruiseByCode(String id);

	/**
	 * Deletes a cruise by calling the deleteCruise method of the CruiseDAO
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteCruise(String id);

	/**
	 * Updates a cruise by calling the updateCruiseByCode method of the CruiseDAO
	 * 
	 * @param cruise
	 * @return
	 */
	public boolean updateCruiseByCode(Cruise cruise);

	/**
	 * Inserts a new cruise by calling the insertCruise method of the CruiseDAO
	 * 
	 * @param cruise
	 * @return
	 */
	public boolean insertCruise(Cruise cruise);

	/**
	 * Checks if a cruise is associated with any worker by calling the
	 * checkCruiseInWorker method of the CruiseDAO implementation with the provided
	 * id and returns the result.
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkCruiseInWorker(String id);

	/**
	 * Checks if a cruise is associated with any booking by calling the
	 * checkCruiseInBook method of the CruiseDAO implementation with the provided id
	 * and returns the result.
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkCruiseInBook(String id);

	public int getNextCruiseCode();
}
