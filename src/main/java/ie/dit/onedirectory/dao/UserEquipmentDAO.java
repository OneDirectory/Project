package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.UserEquipment;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface UserEquipmentDAO {

	public Collection<UserEquipment> getAllUserEquipment();
	public Collection<UserEquipment> getAllModelsFromUserEquipment();
	public void addUserEquipment(UserEquipment userEquipment);
	
}
