package ie.dit.onedirectory.dao.jpa;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ie.dit.onedirectory.dao.FailureClassDAO;
import ie.dit.onedirectory.entities.FailureClass;

@Stateless
@Local
public class JPAFailureClassDAO implements FailureClassDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Collection<FailureClass> getAllFailureClasses() {
		Query q = entityManager.createQuery("from FailureClass");
		return q.getResultList();
	}

	public void addFailureClass(FailureClass failureClass) {
		if(!entityManager.contains(failureClass)){
			entityManager.persist(failureClass);
		}
	}

}
