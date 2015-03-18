/**
 * 
 * This class allows us to perform queries on the database for 
 * failed call data and uses an entity manager linking our entities
 * with the database under the hood.
 * 
 */
package ie.dit.onedirectory.dao.jpa;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

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

	public Collection getFailedCallDataByModel(String model, Date fromDate,
			Date toDate) {
		Query tacQ = entityManager
				.createQuery("select ue.tac from UserEquipment ue where ue.model = :model");
		tacQ.setParameter("model", model);
		Integer tac = (Integer) tacQ.getResultList().get(0);
		Query query = entityManager
				.createQuery("select count(fd.imsi)"
						+ "from FailedCallData fd where fd.userEquipment.tac = :modelTac "
						+ "and fd.dateTime between :fromDate and :toDate");
		query.setParameter("modelTac", tac);
		query.setParameter("fromDate", fromDate, TemporalType.DATE);
		query.setParameter("toDate", toDate, TemporalType.DATE);
		List result = query.getResultList();
		return result;
	}

	/**
	 * Returns a list of phone models from the FailedCallData table according to
	 * their type allocation code (TAC) which is mapped directly to the phone
	 * model. This list is organised with respect to event ID and cause code for
	 * each dropped call.
	 */

	public Collection getEventIdAndCauseCodeByModel(String modelName) {
		Query tacQuery = entityManager
				.createQuery("select ue.tac from UserEquipment ue where "
						+ "ue.model = :modelName");
		tacQuery.setParameter("modelName", modelName);
		Integer typeAllocationCode = (Integer) tacQuery.getResultList().get(0);
		Query query = entityManager
				.createQuery("select fd.eventCause.eventId, fd.eventCause.causeCode, fd.eventCause.description "
						+ "from FailedCallData fd where fd.userEquipment.tac = :typeAllocationCode "
						+ "group by fd.eventCause.eventId, fd.eventCause.causeCode");
		query.setParameter("typeAllocationCode", typeAllocationCode);
		List result = query.getResultList();
		return result;
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
						+ "fd.eventCause.eventId, fd.eventCause.description from FailedCallData fd where fd.imsi= :imsi group by "
						+ "fd.eventCause.causeCode,fd.eventCause.eventId");
		query.setParameter("imsi", imsi);
		List result = query.getResultList();
		return result;

	}

	/**
	 * 
	 * Returns the a list of all IMSIs with the total number of failures
	 * associated with it between a given dates.
	 * 
	 */

	public Collection getCountBetweenDatesForAllIMSI(Date from, Date to) {
		Query query = entityManager
				.createQuery("select fd.imsi, count(fd.imsi), sum(fd.duration)"
						+ "from FailedCallData fd where fd.dateTime between :fromDate  and :toDate"
						+ " group by fd.imsi");
		query.setParameter("fromDate", from, TemporalType.DATE);
		query.setParameter("toDate", to, TemporalType.DATE);
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

	/**
	 * Returns a list of all IMSIs with call failures between the from and to
	 * dates.
	 */

	public Collection getAllIMSIWithCallFailuresBetweenDates(Date from, Date to) {

		Query query = entityManager
				.createQuery("select fd.imsi"
						+ " from FailedCallData fd where fd.dateTime between :fromDate  and :toDate"
						+ " group by fd.imsi");

		query.setParameter("fromDate", from, TemporalType.DATE);
		query.setParameter("toDate", to, TemporalType.DATE);
		List result = query.getResultList();
		return result;
	}

}
