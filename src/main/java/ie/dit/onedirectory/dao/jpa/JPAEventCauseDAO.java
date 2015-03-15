package ie.dit.onedirectory.dao.jpa;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ie.dit.onedirectory.dao.EventCauseDAO;
import ie.dit.onedirectory.entities.EventCause;

@Stateless
@Local
public class JPAEventCauseDAO implements EventCauseDAO {

	@PersistenceContext
	EntityManager entityManager;

	public Collection<EventCause> getAllEventCauses() {
		Query query = entityManager.createQuery("from EventCause");
		Collection<EventCause> eventCauses = query.getResultList();
		return eventCauses;
	}

	public void addEventCause(EventCause eventCause){
		entityManager.persist(eventCause);
	}

	public void addEventCauses(Collection<EventCause> eventCauseList) {
		//entityManager.persist(eventCauseList);
		for(EventCause ec : eventCauseList) {
			entityManager.persist(ec);
		}
	}

}
