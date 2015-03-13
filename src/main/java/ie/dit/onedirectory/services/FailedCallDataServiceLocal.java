/**
 * Provides the link to the the RESTful web service for 
 * all queries relating to failed call data.
 * 
 */

package ie.dit.onedirectory.services;

import ie.dit.onedirectory.entities.FailedCallData;

import java.sql.Date;
import java.util.Collection;

import javax.ejb.Local;

@Local
public interface FailedCallDataServiceLocal {

	public Collection getEventIdAndCauseCodeByModel(Integer typeAllocationCode);
	public Collection getEventIdAndCauseCodeByIMSI(String imsi);
	public Collection getAllIMSI();
	public Collection getCountBetweenDatesForAllIMSI(Date from, Date to);
	public Collection<FailedCallData> getAllFailedCallData();
	public Collection<FailedCallData> getFailedCallDataByModel(String model, Date fromDate, Date toDate);
	public void addFailedCalledDatum(FailedCallData failedCallData);
	public void addFailedCalledData(Collection<FailedCallData> failedCallDataList);
}
