package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.UserEquipment;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface UserEquipmentDAO {

	public Collection<UserEquipment> getAllUserEquipments();
	public void addUserEquipment(UserEquipment userEquipment);
	public void addUserEquipments(Collection<UserEquipment> userEquipmentList);
	
}