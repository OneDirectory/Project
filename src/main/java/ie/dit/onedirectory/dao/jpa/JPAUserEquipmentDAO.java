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
	
	@SuppressWarnings("unchecked")
	public Collection<UserEquipment> getAllUserEquipments() {
		Query q = entityManager.createQuery("select u from UserEquipment as u");
		return q.getResultList();
	}
	
	public void addUserEquipment(UserEquipment userEquipment) {
		entityManager.persist(userEquipment);
	}

	public void addUserEquipments(Collection<UserEquipment> userEquipmentList) {
		entityManager.persist(userEquipmentList);
	}

}
