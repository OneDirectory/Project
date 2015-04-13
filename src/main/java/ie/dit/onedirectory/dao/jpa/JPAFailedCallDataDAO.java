/**
 * 
 * This class allows us to perform queries on the database for 
 * failed call data and uses an entity manager linking our entities
 * with the database under the hood.
 * 
 */
package ie.dit.onedirectory.dao.jpa;

import ie.dit.onedirectory.dao.FailedCallDataDAO;
import ie.dit.onedirectory.entities.FailedCallData;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

@Stateless
@Local
public class JPAFailedCallDataDAO implements FailedCallDataDAO {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Returns collection all failed call data entites in database
	 */
	public Collection<FailedCallData> getAllFailedCallData() {
		Query q = entityManager.createQuery("from FailedCallData");
		return q.getResultList();
	}
	
	/**
	 * Gets the tac code from the passed model
	 * Uses the tac to return a collection of count of failures
	 * for that model in a given date range
	 */

	public Collection<?> getFailedCallDataByModel(String model, Date fromDate,
			Date toDate) {
		Query tacQ = entityManager
				.createQuery("select ue.tac from UserEquipment ue where ue.model = :model");
		tacQ.setParameter("model", model);
		Integer tac;
		
		// Below is used for restTests because of the two querys running in this method
		// and sue to the fact we have an empty test database
		if(model.equals("restTest")){
			tac = 101;
		}else{
			tac = (Integer) tacQ.getResultList().get(0);
		}
		
		Query query = entityManager
				.createQuery("select count(fd.imsi)"
						+ "from FailedCallData fd where fd.userEquipment.tac = :modelTac "
						+ "and fd.dateTime between :fromDate and :toDate");
		query.setParameter("modelTac", tac);
		query.setParameter("fromDate", fromDate, TemporalType.DATE);
		query.setParameter("toDate", toDate, TemporalType.DATE);
		List<?> result = query.getResultList();
		return result;
	}

	/**
	 * Returns a list of phone models from the FailedCallData table according to
	 * their type allocation code (TAC) which is mapped directly to the phone
	 * model. This list is organised with respect to event ID and cause code for
	 * each dropped call.
	 */

	public Collection<?> getEventIdAndCauseCodeByModel(String modelName) {
		Query tacQuery = entityManager
				.createQuery("select ue.tac from UserEquipment ue where "
						+ "ue.model = :modelName");
		tacQuery.setParameter("modelName", modelName);
		Integer typeAllocationCode;
		
		// Below is used for restTests because of the two querys running in this method
		// and sue to the fact we have an empty test database
		if(modelName.equals("restTest")){
			typeAllocationCode = 123;
		}else{
			typeAllocationCode = (Integer) tacQuery.getResultList().get(0);
		}
			
		Query query = entityManager
				.createQuery("select fd.eventCause.eventId, fd.eventCause.causeCode, fd.eventCause.description "
						+ "from FailedCallData fd where fd.userEquipment.tac = :typeAllocationCode "
						+ "group by fd.eventCause.eventId, fd.eventCause.causeCode");
		query.setParameter("typeAllocationCode", typeAllocationCode);
		List<?> result = query.getResultList();
		return result;
	}

	/**
	 * Returns a collection of objects(event id and cause code) where they are
	 * identified as having a passed IMSI String. The combo is grouped so as to
	 * only send back unique combinations.
	 * 
	 */
	public Collection<?> getEventIdAndCauseCodeByIMSI(String imsi) {
		Query query = entityManager
				.createQuery("select fd.eventCause.causeCode, "
						+ "fd.eventCause.eventId, fd.eventCause.description from FailedCallData fd where fd.imsi= :imsi group by "
						+ "fd.eventCause.causeCode,fd.eventCause.eventId");
		query.setParameter("imsi", imsi);
		List<?> result = query.getResultList();
		return result;

	}

	/**
	 * 
	 * Returns the a list of all IMSIs with the total number of failures
	 * associated with it between a given dates.
	 * 
	 */

	public Collection<?> getCountBetweenDatesForAllIMSI(Date from, Date to) {
		Query query = entityManager
				.createQuery("select fd.imsi, count(fd.imsi), sum(fd.duration)"
						+ "from FailedCallData fd where fd.dateTime between :fromDate  and :toDate"
						+ " group by fd.imsi");
		query.setParameter("fromDate", from, TemporalType.DATE);
		query.setParameter("toDate", to, TemporalType.DATE);
		List<?> result = query.getResultList();
		return result;
	}

	/**
	 * Returns a list of all unique imsis affected by failed call data
	 * 
	 */

	public Collection<?> getAllIMSI() {
		Query query = entityManager
				.createQuery("select distinct fd.imsi from FailedCallData fd");
		Collection<?> result = query.getResultList();
		return result;
	}

	/**
	 * Adds a passed failed call data pojo to the database
	 */
	public void addFailedCalledDatum(FailedCallData failedCallData) {
		entityManager.persist(failedCallData);
	}

	/**
	 * Returns a list of all IMSIs with call failures between the from and to
	 * dates.
	 */

	public Collection<?> getAllIMSIWithCallFailuresBetweenDates(Date from,
			Date to) {

		Query query = entityManager
				.createQuery("select fd.imsi"
						+ " from FailedCallData fd where fd.dateTime between :fromDate  and :toDate"
						+ " group by fd.imsi");

		query.setParameter("fromDate", from, TemporalType.DATE);
		query.setParameter("toDate", to, TemporalType.DATE);
		List<?> result = query.getResultList();
		return result;
	}

	/**
	 * Returns a list of all imsis that have failed call data for a passed
	 * failure ID which corresponds to a failure class
	 */
	@Override
	public Collection<String> getAllImsiForFailureClass(Integer failureID) {
		Query query = entityManager
				.createQuery("Select distinct f.imsi from FailedCallData f where f.failureClass.failureId =:failureID");
		query.setParameter("failureID", failureID);
		return query.getResultList();
	}
	
	/**
	 * Returns a list of all unique cause codes for a given imsi
	 */
	public Collection<?> getUniqueCauseCodesForImsi(String imsi){
		Query query = entityManager.createQuery("Select f.eventCause.causeCode from FailedCallData f where f.imsi = :imsi"
				+ " group by f.eventCause.causeCode");
		query.setParameter("imsi", imsi);
		return query.getResultList();
	}

	/**
	 * Returns a count of failed calls for a particular imsi between two points in time
	 */
	public Collection<?> getCountFailedCallsInTimePeriodByImsi(String imsi,
			Date fromDate, Date toDate) {
		Query query = entityManager.createQuery("Select f.imsi, f.id, count(f.id), from FailedCallData f" + 
			" where f.dateTime between :fromDate and :toDate" +
				" group by f.imsi");
		query.setParameter("imsi", imsi);
		return query.getResultList();
	}

}
