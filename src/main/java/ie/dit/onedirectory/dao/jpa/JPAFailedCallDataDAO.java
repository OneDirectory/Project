package ie.dit.onedirectory.dao.jpa;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ie.dit.onedirectory.dao.FailedCallDataDAO;
import ie.dit.onedirectory.entities.FailedCallData;

@Stateless
@Local
public class JPAFailedCallDataDAO implements FailedCallDataDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Collection<FailedCallData> getAllFailedCallData() {
		Query q = entityManager.createQuery("select f from FailedCallData as f");
		return q.getResultList();
	}
	
	public void addFailedCalledDatum(FailedCallData failedCallData) {
		entityManager.persist(failedCallData);
	}
	
	public void addFailedCalledData(
			Collection<FailedCallData> failedCallDataList) {
		entityManager.persist(failedCallDataList);
	}

}
