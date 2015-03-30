package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.EventCause;
import ie.dit.onedirectory.services.EventCauseServiceLocal;
import ie.dit.onedirectory.utilities.FileUploadForm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

// Darrens hard coded location
//home/drrn/Project/data.xls


@Path("/eventcauses")
public class EventCauseREST {

	@EJB
	EventCauseServiceLocal service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<EventCause> getEventCauses(){
		return service.getEventCauses();
	}
	
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public void addEventCauses(@MultipartForm FileUploadForm form) throws IOException{
		HSSFRow row;
		ByteArrayInputStream stream = new ByteArrayInputStream(form.getFileData());
		HSSFWorkbook workbook = new HSSFWorkbook(stream);
		HSSFSheet spreadsheet = workbook.getSheetAt(1);
		Iterator<Row> rowIterator = spreadsheet.iterator();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			row = (HSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				DataFormatter dataFormatter = new DataFormatter();
				Integer causeCode = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
				Integer eventId = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
				String description = cellIterator.next().getStringCellValue();
				service.addEventCause(new EventCause(causeCode, eventId, description));
			}
		}
		workbook.close();
		stream.close();
	}
}
