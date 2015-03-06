package ie.dit.onedirectory.dao.jpa;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
		Query query = entityManager
				.createQuery("from UserEquipment m where m.model= :model");
		query.setParameter("model", model);
		List<FailedCallData> result = query.getResultList();
		return query.getResultList();
	}

	public Collection<FailedCallData> getEventIdAndCauseCodeByModel() {
		Query q = entityManager
				.createQuery("select causeCode from FailedCallData order by typeAllocationCode");
		return q.getResultList();
	}

	public Collection getEventIdAndCauseCodeByIMSI(String imsi) {
		Query query = entityManager
				.createQuery("select fd.eventCause.causeCode, "
						+ "fd.eventCause.eventId from FailedCallData fd where fd.imsi= :imsi");
		query.setParameter("imsi", imsi);
		List result = query.getResultList();
		return result;

	}

	public void addFailedCalledDatum(FailedCallData failedCallData) {
		entityManager.persist(failedCallData);
	}

	public void addFailedCalledData(
			Collection<FailedCallData> failedCallDataList) {
		entityManager.persist(failedCallDataList);
	}

}
