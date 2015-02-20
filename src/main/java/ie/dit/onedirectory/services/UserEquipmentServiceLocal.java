package ie.dit.onedirectory.services;

import ie.dit.onedirectory.entities.UserEquipment;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface UserEquipmentServiceLocal {

	public Collection<UserEquipment> getAllUserEquipments();
	public void addUserEquipment(UserEquipment userEquipment);
	public void addUserEquipments(Collection<UserEquipment> userEquipmentList);
	
}