package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.FailedCallData;

import java.util.Collection;


import javax.ejb.Local;

@Local
public interface FailedCallDataDAO {

	public Collection<FailedCallData> getEventIdAndCauseCodeByModel();
	public Collection<FailedCallData> getAllFailedCallData();
	public Collection<FailedCallData> getFailedCallDataByModel(String model);
	public Collection getEventIdAndCauseCodeByIMSI(String imsi);
	public Collection<String> getAllIMSI();
	public void addFailedCalledDatum(FailedCallData failedCallData);
	public void addFailedCalledData(Collection<FailedCallData> failedCallDataList);
	
	
}
