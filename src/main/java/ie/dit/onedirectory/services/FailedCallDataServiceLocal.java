package ie.dit.onedirectory.services;

import ie.dit.onedirectory.entities.FailedCallData;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

@Local
public interface FailedCallDataServiceLocal {

	public Collection<FailedCallData> getEventIdAndCauseCodeByModel();
	public Collection getEventIdAndCauseCodeByIMSI(String imsi);
	public Collection<FailedCallData> getAllFailedCallData();
	public Collection<FailedCallData> getFailedCallDataByModel(String model);
	public void addFailedCalledDatum(FailedCallData failedCallData);
	public void addFailedCalledData(Collection<FailedCallData> failedCallDataList);
}
