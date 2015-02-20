package ie.dit.onedirectory.services;

import ie.dit.onedirectory.entities.FailedCallData;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface FailedCallDataServiceLocal {

	public Collection<FailedCallData> getAllFailedCallData();
	public void addFailedCalledDatum(FailedCallData failedCallData);
	public void addFailedCalledData(Collection<FailedCallData> failedCallDataList);
}