package ie.dit.onedirectory.services.ejbs;

import java.util.Collection;

import ie.dit.onedirectory.dao.UserEquipmentDAO;
import ie.dit.onedirectory.entities.UserEquipment;
import ie.dit.onedirectory.services.UserEquipmentServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class UserEquipmentServiceLocalEJB implements UserEquipmentServiceLocal {

	@EJB
	private UserEquipmentDAO dao;
	
	public void setDao(UserEquipmentDAO dao) {
		this.dao = dao;
	}
	
	public Collection<UserEquipment> getAllUserEquipment() {
		return dao.getAllUserEquipment();
	}
	
	public Collection<UserEquipment> getAllModelsFromUserEquipment(){
		return dao.getAllModelsFromUserEquipment();
	}

	public void addUserEquipment(UserEquipment userEquipment) {
		dao.addUserEquipment(userEquipment);
	}

	public void addUserEquipments(Collection<UserEquipment> userEquipmentList) {
		dao.addUserEquipments(userEquipmentList);
	}

}
