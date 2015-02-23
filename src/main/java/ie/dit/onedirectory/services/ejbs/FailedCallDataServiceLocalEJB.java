package ie.dit.onedirectory.services.ejbs;

import java.util.Collection;

import ie.dit.onedirectory.dao.FailedCallDataDAO;
import ie.dit.onedirectory.entities.FailedCallData;
import ie.dit.onedirectory.services.FailedCallDataServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class FailedCallDataServiceLocalEJB implements FailedCallDataServiceLocal {

	@EJB
	private FailedCallDataDAO dao;
	
	public void setDao(FailedCallDataDAO dao) {
		this.dao = dao;
	}

	public Collection<FailedCallData> getAllFailedCallData() {
		return dao.getAllFailedCallData();
	}

	public void addFailedCalledDatum(FailedCallData failedCallData) {
		dao.addFailedCalledDatum(failedCallData);
	}
	
	public void addFailedCalledData(
			Collection<FailedCallData> failedCallDataList) {
		dao.addFailedCalledData(failedCallDataList);
	}

}
