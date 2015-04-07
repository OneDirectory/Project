/**
 * User equipment data access implementation 
 */
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
	
	/**
	 * Returns all user equipment objects
	 */
	
	public Collection<UserEquipment> getAllUserEquipment() {
		Query q = entityManager.createQuery("from UserEquipment");
		return q.getResultList();
	}
	
	
	public Collection<UserEquipment> getAllModelsFromUserEquipment(){
		Query query = entityManager.createQuery("Select model from UserEquipment");
		return query.getResultList();
	}
	
	/**
	 * Adds user euipment pojo if not already exists using entity manager
	 */
	
	public void addUserEquipment(UserEquipment userEquipment) {
		if(!entityManager.contains(userEquipment)){
			entityManager.persist(userEquipment);
		}
	}

}
