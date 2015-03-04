package ie.dit.onedirectory.dao.jpa;

import java.util.Collection;
import java.util.List;

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

	public Collection<FailedCallData> getAllFailedCallData() {
		Query q = entityManager.createQuery("from FailedCallData");
		return q.getResultList();
	}
	
	public Collection<FailedCallData> getFailedCallDataByModel(String model) {
		Query query  = entityManager.createQuery("from UserEquipment m where m.model= :model");
		query.setParameter("model", model);
		List<FailedCallData> result = query.getResultList();
		return query.getResultList();
	}
	
	public void addFailedCalledDatum(FailedCallData failedCallData) {
		entityManager.persist(failedCallData);
	}
	
	public void addFailedCalledData(
			Collection<FailedCallData> failedCallDataList) {
		entityManager.persist(failedCallDataList);
	}

}
