package model;

import java.util.List;

/**
 * Data Access Object interface identifying operations for Worker data.
 * 
 * @author Etna
 */
public interface WorkerDAO {
	/**
	 * This method retrieves a list of all workers from the database.
	 * 
	 * @return
	 */
	public List<Worker> getAllWorker();

	/**
	 * This method deletes a worker from the database based on their unique
	 * identifier (id).
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteWorker(String id);

	/**
	 * This method updates the information of a worker in the database based on
	 * their unique identifier (id).
	 * 
	 * @param worker
	 * @return
	 */
	public boolean updateWorker(Worker worker);

	/**
	 * This method inserts a new worker into the database based on the provided Worker object.
	 * @param worker
	 * @return
	 */
	public boolean insertWorker(Worker worker);

	public boolean idWorkerExists(String id);

	public boolean phoneWorkerExists(String phone);

	public boolean emailWorkerExists(String email);


}
