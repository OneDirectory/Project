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

	public Collection getAllIMSIWithCallFailuresBetweenDates(Date from, Date to);
	public Collection getEventIdAndCauseCodeByModel(String modelName);
	public Collection<FailedCallData> getAllFailedCallData();
	public Collection getFailedCallDataByModel(String model, Date fromDate, Date toDate);
	public Collection getEventIdAndCauseCodeByIMSI(String imsi);
	public Collection getAllIMSI();
	public Collection getCountBetweenDatesForAllIMSI(Date from, Date to);
	public void addFailedCalledDatum(FailedCallData failedCallData);
	public void addFailedCalledData(Collection<FailedCallData> failedCallDataList);
		
}
