package ie.dit.onedirectory.dao.jpa;

import ie.dit.onedirectory.dao.UserEquipmentDAO;
import ie.dit.onedirectory.entities.UserEquipment;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local
public class JPAUserEquipmentDAO implements UserEquipmentDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Collection<UserEquipment> getAllUserEquipment() {
		Query q = entityManager.createQuery("from UserEquipment");
		return q.getResultList();
	}
	
	public void addUserEquipment(UserEquipment userEquipment) {
		if(!entityManager.contains(userEquipment)){
			entityManager.persist(userEquipment);
		}
	}

	public void addUserEquipments(Collection<UserEquipment> userEquipmentList) {
		entityManager.merge(userEquipmentList);
	}


}
