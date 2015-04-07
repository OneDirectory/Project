/**
 * Access to the database for event cause records and implements our dao interface
 */
package ie.dit.onedirectory.dao.jpa;

import ie.dit.onedirectory.dao.EventCauseDAO;
import ie.dit.onedirectory.entities.EventCause;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local
public class JPAEventCauseDAO implements EventCauseDAO {

	@PersistenceContext
	EntityManager entityManager;

	/**
	 * Get all eventcauses entities using entity manager
	 */
	public Collection<EventCause> getAllEventCauses() {
		Query query = entityManager.createQuery("from EventCause");
		Collection<EventCause> eventCauses = query.getResultList();
		return eventCauses;
	}

	/**
	 * add a passed event causes pojo using entity manager
	 */
	public void addEventCause(EventCause eventCause) {
		if (!entityManager.contains(eventCause)) {
			entityManager.persist(eventCause);
		}
	}

}
