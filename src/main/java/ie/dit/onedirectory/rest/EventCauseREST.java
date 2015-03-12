package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.EventCause;
import ie.dit.onedirectory.services.EventCauseServiceLocal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

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
	
	@GET
	@Path("/add")
	public void addEventCauses() throws IOException{
		HSSFRow row;
		//FileInputStream fis = new FileInputStream(new File("C:/oneDirectory/data.xls"));
		FileInputStream fis = new FileInputStream(new File("/home/drrn/Project/data.xls"));
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
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
		fis.close();
	}
}
