/**
 * This class provides access from the client side via a
 * RESTful web service to insert failed call data into the
 * database and to return queries by the client from the database.
 * 
 * A service interface is injected to provide access to the service
 * layer.
 * 
 * Returns a list of all IMSIs
 * Returns a list of all unique cause code and eventID combinations
 * affecting an IMSI.
 * 
 */

package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.FailedCallData;
import ie.dit.onedirectory.services.FailedCallDataServiceLocal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	public Collection<FailedCallData> getFailedCallData() {
		return service.getAllFailedCallData();
	}

	/**
	 * @param typeAllocationCode
	 * Takes a String that specifies which model of phone is being examined
	 * @return An object containing eventID and CauseCode in JSON format to the
	 * client from the service layer.
	 * 
	 */
	
	@GET
	@Path("/model/{getEventIdAndCauseCodeByModel}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailedCallData> getEventIdAndCauseCodeByModel(
			@PathParam("model") String typeAllocationCode) {
		return service.getEventIdAndCauseCodeByModel(typeAllocationCode);
	}

	/**
	 * @param imsi
	 *            Takes a String specifying which IMSI should be sent to the
	 *            service layer for the query.
	 * @return An object containing eventID and CauseCode in JSON format to the
	 *         client from the service layer.
	 *         
	 */

	@GET
	@Path("/imsi/{imsi}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection getEventCauseByIMSI(@PathParam("imsi") String imsi) {
		return service.getEventIdAndCauseCodeByIMSI(imsi);
	}
	@GET
	@Path("/count")
	@Produces
	public Collection getCountBetweenDatesForAllIMSI() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = "10/01/2013 10:20:56";
		Date from = sdf.parse(date);
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		String date1 = "12/01/2013 10:20:56";
		Date to = sdf1.parse(date1);
		java.sql.Date sqlDateFrom = new java.sql.Date(from.getTime());
		java.sql.Date sqlDateTo = new java.sql.Date(to.getTime());
		return service.getCountBetweenDatesForAllIMSI(sqlDateFrom, sqlDateTo);
	}

	/**
	 * @return A a collection of String JSON representations of all IMSIs to the
	 *         client from the service layer.
	 * 
	 */

	@GET
	@Path("/imsi")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection getAllIMSI() {

		return service.getAllIMSI();
	}

	@GET
	@Path("/models/{getFailedCallDataByModel}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailedCallData> getFailedCallDataByModel(
			@QueryParam("model") String model) {
		return service.getFailedCallDataByModel(model);
	}

	@GET
	@Path("/add")
	public void addFailedCallData() throws IOException {
		HSSFRow row;
		FileInputStream fis = new FileInputStream(new File(
				"/Users/Darren/Project/data.xls"));
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet spreadsheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = spreadsheet.iterator();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			row = (HSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				DataFormatter dataFormatter = new DataFormatter();
				Date dateTime = HSSFDateUtil.getJavaDate(cellIterator.next()
						.getNumericCellValue());
				Integer eventId = Integer.valueOf(dataFormatter
						.formatCellValue(cellIterator.next()));
				String failureString = dataFormatter
						.formatCellValue(cellIterator.next());
				Integer failureId;
				if (failureString.equals("(null)")) {
					break;
				} else {
					failureId = Integer.valueOf(failureString);
				}
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
				if (failureString.equals("(null)")) {
					break;
				} else {
					causeCode = Integer.valueOf(causeString);
				}
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

				service.addFailedCalledDatum(new FailedCallData(dateTime,
						eventId, failureId, typeAllocationCode, marketId,
						operatorId, cellId, duration, causeCode,
						networkElementVersion, imsi, hier3Id, hier32Id,
						hier321Id));
				break;
			}
		}
		fis.close();
	}

}
