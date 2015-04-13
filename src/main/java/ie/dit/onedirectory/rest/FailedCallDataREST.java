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
import ie.dit.onedirectory.utilities.DataValidator;
import ie.dit.onedirectory.utilities.FileUploadForm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/failedcalldata")
public class FailedCallDataREST {

	@EJB
	FailedCallDataServiceLocal service;

	@EJB
	DataValidator validator;

	/**
	 * Get Request
	 * 
	 * @return A JSON collection of all failed call data objects
	 */

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailedCallData> getFailedCallData() {
		return service.getAllFailedCallData();
	}

	/**
	 * @param model
	 *            Takes a String that specifies which model of phone is being
	 *            examined
	 * @return An object containing eventID and CauseCode in JSON format to the
	 *         client from the service layer.
	 * 
	 */

	@GET
	@Path("/model/{model}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection getEventIdAndCauseCodeByModel(
			@PathParam("model") String modelName) {
		return service.getEventIdAndCauseCodeByModel(modelName);
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
	public Collection<?> getEventCauseByIMSI(@PathParam("imsi") String imsi) {
		return service.getEventIdAndCauseCodeByIMSI(imsi);
	}
	
	@GET
	@Path("/uniqueCauseCodes/{imsi)")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<?> getUniqueCauseCodesForImsi(@PathParam("imsi")String imsi){
		return service.getUniqueCauseCodesForImsi(imsi);
	}

	// JF addition
	@GET
	@Path("/dateIMSI/{dates2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<?> getAllIMSIWithCallFailuresBetweenDates(
			@PathParam("dates2") String datesPassed) throws ParseException {

		String[] dates2 = datesPassed.split("£");
		String fromDate = dates2[0];
		String toDate = dates2[1];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date from = sdf.parse(fromDate);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date to = sdf1.parse(toDate);
		java.sql.Date sqlDateFrom = new java.sql.Date(from.getTime());
		java.sql.Date sqlDateTo = new java.sql.Date(to.getTime());
		return service.getAllIMSIWithCallFailuresBetweenDates(sqlDateFrom,
				sqlDateTo);
	}

	/**
	 * The passed string is parsed to get the two dates Two date objects are
	 * created and converted to sql dates for database access
	 * 
	 * @param datesPassed
	 *            search range requested by a user
	 * @return A JSON collection of the total count of failed call data for all
	 *         imsis
	 * @throws ParseException
	 */
	@GET
	@Path("/count/{dates}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection getCountBetweenDatesForAllIMSI(
			@PathParam("dates") String datesPassed) throws ParseException {

		String[] dates = datesPassed.split("£");
		String fromDate = dates[0];
		String toDate = dates[1];

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date from = sdf.parse(fromDate);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date to = sdf1.parse(toDate);
		java.sql.Date sqlDateFrom = new java.sql.Date(from.getTime());
		java.sql.Date sqlDateTo = new java.sql.Date(to.getTime());

		return service.getCountBetweenDatesForAllIMSI(sqlDateFrom, sqlDateTo);

	}
	
	/**
	 * @return A collection of the counts of failed calls for each IMSI
	 * @throws ParseException 
	 */
	@GET
	@Path("/getCountFailedCallsInTimePeriodByImsi/{params}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<?> getCountFailedCallsInTimePeriodByImsi(@PathParam("params") String paramsPassed) throws ParseException {
		
		String[] params = paramsPassed.split("£");
		String imsi = params[0];
		String fromDate = params[1];
		String toDate = params[2];
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date from = sdf.parse(fromDate);
		Date to = sdf1.parse(toDate);
		
		java.sql.Date sqlFromDate = new java.sql.Date(from.getTime());
		java.sql.Date sqlToDate = new java.sql.Date(to.getTime());
		return service.getCountFailedCallsInTimePeriodByImsi(imsi, sqlFromDate, sqlToDate);
	}

	/**
	 * @return A collection of String JSON representations of all IMSIs to the
	 *         client from the service layer.
	 * 
	 */

	@GET
	@Path("/imsi")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<?> getAllIMSI() {
		return service.getAllIMSI();
	}

	/**
	 * GET request
	 * 
	 * @param failureID
	 *            passed failure id string parsed to an Integer
	 * @return A collection of all imsis with a failed call data due to that
	 *         failureID which represents a failure class
	 */
	@GET
	@Path("/imsibyfailureclass/{failureId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<String> getAllImsiForFailureClass(
			@PathParam("failureId") String failureID) {
		return service.getAllImsiForFailureClass(Integer.parseInt(failureID));
	}

	/**
	 * Passed string is parsed to extract dates and model
	 * 
	 * @param paramsPassed
	 *            passed string of two dates and a model
	 * @return a collection of failed call data for that model within date
	 *         range
	 * @throws ParseException
	 */
	@GET
	@Path("/getFailedCallDataByModel/{params}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<?> getFailedCallDataByModel(
			@PathParam("params") String paramsPassed) throws ParseException {
		String[] params = paramsPassed.split("£");
		String model = params[0];
		String fromDate = params[1];
		String toDate = params[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date from = sdf.parse(fromDate);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date to = sdf1.parse(toDate);
		java.sql.Date sqlFromDate = new java.sql.Date(from.getTime());
		java.sql.Date sqlToDate = new java.sql.Date(to.getTime());
		return service.getFailedCallDataByModel(model, sqlFromDate, sqlToDate);
	}

	@GET
	@Path("/topTenMOCombinations/{dates:.+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<?> getTopTenMarketOperatorCellIDCombinations(
			@PathParam("dates") String dateString) throws ParseException{
		String[] dates = dateString.split("£");
		String fromDate = dates[0];
		String toDate = dates[1];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date from = sdf.parse(fromDate);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date to = sdf1.parse(toDate);
		java.sql.Date sqlDateFrom = new java.sql.Date(from.getTime());
		java.sql.Date sqlDateTo = new java.sql.Date(to.getTime());

		return service.getTopTenMarketOperatorCellIDCombinations(sqlDateFrom, sqlDateTo);
		
	}
	
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public Response uploadFailedCallData(@MultipartForm FileUploadForm form)
			throws IOException {
		HSSFRow row;
		ByteArrayInputStream stream = new ByteArrayInputStream(
				form.getFileData());
		HSSFWorkbook workbook = new HSSFWorkbook(stream);
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

				if (validator.validFailureIdAndCauseCodeTypes(failureString,
						causeString)) {
					causeCode = Integer.valueOf(causeString);
					failureId = Integer.valueOf(failureString);
				} else {
					break;
				}

				FailedCallData failedCallData = new FailedCallData(dateTime,
						eventId, failureId, typeAllocationCode, marketId,
						operatorId, cellId, duration, causeCode,
						networkElementVersion, imsi, hier3Id, hier32Id,
						hier321Id);
				if (validator.isValid(failedCallData)) {
					service.addFailedCalledDatum(failedCallData);
				}
				break;
			}
		}
		stream.close();
		workbook.close();
		return Response.status(200).entity("Data successfully imported.\n")
				.build();
	}

}
