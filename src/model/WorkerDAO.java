package model;

import java.util.List;

public interface WorkerDAO {
	public List<Worker> getAllWorker();
}// This interface defines a contract for data access operations related to workers
// It declares a method getAllWorker() that returns a list of Worker objects, which represents all the workers retrieved from the database. 
//Any class that implements this interface must provide an implementation for the getAllWorker() method, allowing for different ways to access worker data.
