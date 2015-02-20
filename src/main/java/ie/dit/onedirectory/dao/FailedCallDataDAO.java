package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.FailedCallData;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface FailedCallDataDAO {

	public Collection<FailedCallData> getAllFailedCallData();
	public void addFailedCalledDatum(FailedCallData failedCallData);
	public void addFailedCalledData(Collection<FailedCallData> failedCallDataList);
}
