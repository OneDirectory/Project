/**
 * 
 * A DAO interface is injected to provide access to
 * the DAO layer and implements an interface to link
 * with the RESTful web service.
 * 
 */

package ie.dit.onedirectory.services.ejbs;

import ie.dit.onedirectory.dao.FailedCallDataDAO;
import ie.dit.onedirectory.entities.FailedCallData;
import ie.dit.onedirectory.services.FailedCallDataServiceLocal;
import ie.dit.onedirectory.utilities.DataValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

@Stateless
@Local
public class FailedCallDataServiceLocalEJB implements FailedCallDataServiceLocal {

	@EJB
	private FailedCallDataDAO dao;
	
	@EJB
	DataValidator validator;
	
	public void setDao(FailedCallDataDAO dao) {
		this.dao = dao;
	}

	public Collection<?> getEventIdAndCauseCodeByModel(String modelName) {
		return dao.getEventIdAndCauseCodeByModel(modelName);
	}
	
	public Collection<FailedCallData> getAllFailedCallData() {
		return dao.getAllFailedCallData();
	}
	
	public Collection<?> getFailedCallDataByModel(String model, Date fromDate, Date toDate){
		return dao.getFailedCallDataByModel(model, fromDate, toDate);
	}
	
	public Collection<?> getEventIdAndCauseCodeByIMSI(String imsi) {
		return dao.getEventIdAndCauseCodeByIMSI(imsi);
	}
	
	public Collection<?> getAllIMSI(){
		return dao.getAllIMSI();
	}
	
	public void addFailedCalledDatum(FailedCallData failedCallData) {
		dao.addFailedCalledDatum(failedCallData);
	}
	
	public Collection<?> getCountBetweenDatesForAllIMSI(Date from, Date to) {
		return dao.getCountBetweenDatesForAllIMSI(from, to);
	}
	
	public Collection<?> getAllIMSIWithCallFailuresBetweenDates(Date from, Date to) {
		return dao.getAllIMSIWithCallFailuresBetweenDates(from, to);
	}
	
	public void addFromFile(String fileName) throws IOException {
		HSSFRow row;
		FileInputStream fis = new FileInputStream(new File(fileName));
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet spreadsheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = spreadsheet.iterator();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			row = (HSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				DataFormatter dataFormatter = new DataFormatter();
				java.util.Date dateTime = HSSFDateUtil.getJavaDate(cellIterator.next()
						.getNumericCellValue());
				Integer eventId = Integer.valueOf(dataFormatter
						.formatCellValue(cellIterator.next()));
				String failureString = dataFormatter
						.formatCellValue(cellIterator.next());
				Integer failureId;
				Integer typeAllocationCode = Integer.valueOf(dataFormatter
						.formatCellValue(cellIterator.next()));
				Integer marketId = Integer.valueOf(dataFormatter
						.formatCellValue(cellIterator.next()));
				Integer operatorId = Integer.valueOf(dataFormatter
						.formatCellValue(cellIterator.next()));
				Integer cellId = Integer.valueOf(dataFormatter
						.formatCellValue(cellIterator.next()));
				Integer duration = Integer.valueOf(dataFormatter
						.formatCellValue(cellIterator.next()));
				String causeString = dataFormatter.formatCellValue(cellIterator
						.next());
				Integer causeCode;
				String networkElementVersion = dataFormatter
						.formatCellValue(cellIterator.next());
				String imsi = dataFormatter
						.formatCellValue(cellIterator.next());
				String hier3Id = dataFormatter.formatCellValue(cellIterator
						.next());
				String hier32Id = dataFormatter.formatCellValue(cellIterator
						.next());
				String hier321Id = dataFormatter.formatCellValue(cellIterator
						.next());
				
				if(validator.validFailureIdAndCauseCodeTypes(failureString, causeString)){
					causeCode = Integer.valueOf(causeString);
					failureId = Integer.valueOf(failureString);
				}
				else {
					break;
				}
				
				FailedCallData failedCallData = new FailedCallData(dateTime,
						eventId, failureId, typeAllocationCode, marketId,
						operatorId, cellId, duration, causeCode,
						networkElementVersion, imsi, hier3Id, hier32Id,
						hier321Id);
				if(validator.isValid(failedCallData)){
					dao.addFailedCalledDatum(failedCallData);
				}
				break;
			}
		}
		workbook.close();
		fis.close();
	}

	@Override
	public Collection<String> getAllImsiForFailureClass(Integer failureClass) {
		return dao.getAllImsiForFailureClass(failureClass);
	}
}
