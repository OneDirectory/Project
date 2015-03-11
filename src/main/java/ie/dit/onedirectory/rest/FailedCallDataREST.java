package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.FailedCallData;
import ie.dit.onedirectory.services.FailedCallDataServiceLocal;
import ie.dit.onedirectory.utilities.FileUploadForm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.jboss.marshalling.ByteInputStream;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/failedcalldata")
public class FailedCallDataREST {

	@EJB
	FailedCallDataServiceLocal service;

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailedCallData> getFailedCallData() {
		return service.getAllFailedCallData();
	}

	// TODO - Peter
	// @GET
	// @Path("/{model}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public Collection<FailedCallData>
	// getEventCauseByModel(@PathParam("model") String model){
	// return service.getEventCauseByModel();
	// }

	// TODO
	// @GET
	// @Path("/{imsi}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public Collection<FailedCallData> getEventCauseByIMSI(@PathParam("imsi")
	// String imsi){
	// return service.getEventCauseByIMSI(imsi);
	// }

	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public Response uploadFailedCallData(@MultipartForm FileUploadForm form)
			throws IOException {
		
		HSSFRow row;
		ByteArrayInputStream stream = new ByteArrayInputStream(form.getFileData());
		HSSFWorkbook workbook= new HSSFWorkbook(stream);
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
		stream.close();
		workbook.close();
		return Response.status(200).entity("Data successfully imported.\n").build();
	}

	private String parseFileName(MultivaluedMap<String, String> headers) {
		String[] contentDispositionHeader = headers.getFirst(
				"Content-Disposition").split(";");
		for (String name : contentDispositionHeader) {
			if ((name.trim().startsWith("fileName"))) {
				String[] tmp = name.split("=");
				String fileName = tmp[1].trim().replaceAll("\"", "");
				return fileName;
			}
		}
		return "randomName";
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
