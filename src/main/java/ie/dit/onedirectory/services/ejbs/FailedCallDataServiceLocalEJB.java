/**
 * 
 * A DAO interface is injected to provide access to
 * the DAO layer and implements an interface to link
 * with the RESTful web service.
 * 
 */

package ie.dit.onedirectory.services.ejbs;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

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

	public Collection<FailedCallData> getEventIdAndCauseCodeByModel(String typeAllocationCode) {
		return dao.getEventIdAndCauseCodeByModel(typeAllocationCode);
	}
	
	public Collection<FailedCallData> getAllFailedCallData() {
		return dao.getAllFailedCallData();
	}
	
	public Collection<FailedCallData> getFailedCallDataByModel(String model){
		return dao.getFailedCallDataByModel(model);
	}
	
	public Collection getEventIdAndCauseCodeByIMSI(String imsi) {
		return dao.getEventIdAndCauseCodeByIMSI(imsi);
	}
	
	public Collection getAllIMSI(){
		return dao.getAllIMSI();
	}
	
	public void addFailedCalledDatum(FailedCallData failedCallData) {
		dao.addFailedCalledDatum(failedCallData);
	}
	
	public void addFailedCalledData(
			Collection<FailedCallData> failedCallDataList) {
		dao.addFailedCalledData(failedCallDataList);
	}

	
	public Collection getCountBetweenDatesForAllIMSI(Date from, Date to) {
		return dao.getCountBetweenDatesForAllIMSI(from, to);
	}
	
	// JF Addition

	public Collection getAllIMSIWithCallFailuresBetweenDates(Date from, Date to) {
		return dao.getAllIMSIWithCallFailuresBetweenDates(from, to);
	}
	
}
