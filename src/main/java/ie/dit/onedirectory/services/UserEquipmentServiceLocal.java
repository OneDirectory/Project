package ie.dit.onedirectory.services;

import ie.dit.onedirectory.entities.UserEquipment;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface UserEquipmentServiceLocal {

	public Collection<UserEquipment> getAllUserEquipment();
	public Collection<UserEquipment> getAllModelsFromUserEquipment();
	public void addUserEquipment(UserEquipment userEquipment);
	public void addUserEquipments(Collection<UserEquipment> userEquipmentList);
	
}
