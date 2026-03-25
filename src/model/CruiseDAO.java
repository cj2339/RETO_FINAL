package model;

import java.util.ArrayList;
import java.util.List;

public interface CruiseDAO {
	
	public List<Cruise> getAllCruise();
	public Cruise getCruiseByCode(String id);
	public boolean deleteCruise(String id);
	public boolean updateCruiseByCode(Cruise cruise);
}
