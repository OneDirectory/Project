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
		Query q = entityManager.createQuery("select f from FailureClass as f");
		return q.getResultList();
	}

	public void addFailureClass(FailureClass failureClass) {
		entityManager.persist(failureClass);
	}

	public void addFailureClasses(Collection<FailureClass> failureClassList) {
		entityManager.persist(failureClassList);		
	}

}
