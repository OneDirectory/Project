/**
 * 
 * This class allows us to perform queries on the database for 
 * failed call data and uses an entity manager linking our entities
 * with the database under the hood.
 * 
 */
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
		Query query = entityManager
				.createQuery("from UserEquipment m where m.model= :model");
		query.setParameter("model", model);
		List<FailedCallData> result = query.getResultList();
		return query.getResultList();
	}

	/**
	 * Returns a list of phone models from the FailedCallData table. This list is
	 * organised with respect to event ID and cause code for each dropped call.
	 */
	
	public Collection<FailedCallData> getEventIdAndCauseCodeByModel(String typeAllocationCode) {
		Query q = entityManager
				.createQuery("from FailedCallData f where f.typeAllocationCode = :typeAllocationCode");
		q.setParameter("typeAllocationCode", typeAllocationCode);
		List<FailedCallData> result = q.getResultList();
		return q.getResultList();
	}

	/**
	 * Returns a collection of objects(event id and cause code) where they are
	 * identified as having a passed IMSI String. The combo is grouped so as to
	 * only send back unique combinations.
	 * 
	 */
	public Collection getEventIdAndCauseCodeByIMSI(String imsi) {
		Query query = entityManager
				.createQuery("select fd.eventCause.causeCode, "
						+ "fd.eventCause.eventId from FailedCallData fd where fd.imsi= :imsi group by "
						+ "fd.eventCause.causeCode,fd.eventCause.eventId");
		query.setParameter("imsi", imsi);
		List result = query.getResultList();
		return result;

	}

	/**
	 * Returns a list of all unique imsis affected by failed call data
	 * 
	 */

	public Collection getAllIMSI() {
		Query query = entityManager
				.createQuery("select distinct fd.imsi from FailedCallData fd");
		Collection result = query.getResultList();
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
