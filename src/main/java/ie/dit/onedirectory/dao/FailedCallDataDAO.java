/**
 * Provides the link to the service layer.
 * 
 */

package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.FailedCallData;

import java.sql.Date;
import java.util.Collection;

import javax.ejb.Local;

@Local
public interface FailedCallDataDAO {
	
	/**
	 * 
	 * @param from date parameter from a point in time
	 * @param to date parameter to a point in time
	 * @return a collection of all imsis within that range
	 */

	public Collection<?> getAllIMSIWithCallFailuresBetweenDates(Date from, Date to);
	
	/**
	 * 
	 * @param modelName takes the name of a mode
	 * @return a collection of eventIDs and cause codes for that model
	 */
	public Collection<?> getEventIdAndCauseCodeByModel(String modelName);
	
	/**
	 * 
	 * @return collection of all failed call data records
	 */
	public Collection<FailedCallData> getAllFailedCallData();
	
	/**
	 * 
	 * 
	 * @param model The model of phone
	 * @param fromDate 
	 * @param toDate
	 * @return failed call data for that model within the range
	 */
	public Collection<?> getFailedCallDataByModel(String model, Date fromDate, Date toDate);
	
	/**
	 * 
	 * @param imsi an imsi string
	 * @return collection of event id's and cause codes for that imsi
	 */
	public Collection<?> getEventIdAndCauseCodeByIMSI(String imsi);
	
	/**
	 * 
	 * @return a collection of all imsis
	 */
	public Collection<?> getAllIMSI();
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @return the number of call failures for all imsis within date range
	 */
	public Collection<?> getCountBetweenDatesForAllIMSI(Date from, Date to);
	
	/**
	 * 
	 * @param failedCallData Adds a failed call data records
	 */
	public void addFailedCalledDatum(FailedCallData failedCallData);
	
	/**
	 * 
	 * @param failureClass takes a failure class id
	 * @return a collection of all imsis with failures due to that failure id
	 */
	public Collection<String> getAllImsiForFailureClass(Integer failureClass);
		
}
