package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.FailedCallData;
import ie.dit.onedirectory.services.FailedCallDataServiceLocal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

@Path("/failedcalldata")
public class FailedCallDataREST {
	
	@EJB
	FailedCallDataServiceLocal service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailedCallData> getFailedCallData(){
		return service.getAllFailedCallData();
	}
	
	@GET
	@Path("/{model}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailedCallData> getEventIdAndCauseCodeByModel(@PathParam("model") String model){
		return service.getEventIdAndCauseCodeByModel();
	}
	
	//TODO
//	@GET
//	@Path("/{imsi}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Collection<FailedCallData> getEventCauseByIMSI(@PathParam("imsi") String imsi){
//		return service.getEventCauseByIMSI(imsi);
//	}
	
	
	@GET
	@Path("/add")
	public void addFailedCallData() throws IOException{
		HSSFRow row;
		FileInputStream fis = new FileInputStream(new File("C://oneDirectory/data.xls"));
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet spreadsheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = spreadsheet.iterator();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			row = (HSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				DataFormatter dataFormatter = new DataFormatter();
				Date dateTime = HSSFDateUtil.getJavaDate(cellIterator.next().getNumericCellValue());
				Integer eventId = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
				String failureString = dataFormatter.formatCellValue(cellIterator.next());
				Integer failureId;
				if(failureString.equals("(null)")){
					break;
				}
				else {
					failureId = Integer.valueOf(failureString);
				}
				Integer typeAllocationCode = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
				Integer marketId = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
				Integer operatorId = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
				Integer cellId = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
				Integer duration = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
				String causeString = dataFormatter.formatCellValue(cellIterator.next());
				Integer causeCode;
				if(failureString.equals("(null)")){
					break;
				}
				else {
					causeCode = Integer.valueOf(causeString);
				}
				String networkElementVersion = dataFormatter.formatCellValue(cellIterator.next());
				String imsi = dataFormatter.formatCellValue(cellIterator.next());
				service.addFailedCalledDatum(new FailedCallData(dateTime, eventId, failureId, typeAllocationCode, 
						marketId, operatorId, cellId, duration, causeCode, networkElementVersion, imsi));
				break;
			}
		}
		fis.close();
	}
	
}
