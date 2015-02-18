package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.EventCause;
import ie.dit.onedirectory.entities.MarketOperator;
import ie.dit.onedirectory.services.MarketOperatorServiceLocal;

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

@Path("/marketoperators")
public class MarketOperatorREST {

	@EJB
	MarketOperatorServiceLocal service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<MarketOperator> getMarketOperators(){
		return service.getMarketOperators();
	}
	
	@GET
	@Path("/add")
	public void addEventCauses() throws IOException{
		HSSFRow row;
		FileInputStream fis = new FileInputStream(new File("/home/drrn/Project/data.xls"));
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet spreadsheet = workbook.getSheetAt(4);
		Iterator<Row> rowIterator = spreadsheet.iterator();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			row = (HSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				DataFormatter dataFormatter = new DataFormatter();
				Integer marketId = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
				Integer operatorId = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
				String country = cellIterator.next().getStringCellValue();
				String operatorName = cellIterator.next().getStringCellValue();
//				EventCause eventCause = new EventCause(causeCode, eventId, description);
//				System.out.println(eventCause.getEventId() + "\t" +  eventCause.getCauseCode() + "\t" + eventCause.getDescription());
				service.addMarketOperator(new MarketOperator(marketId, operatorId, country, operatorName));
			}
		}
		fis.close();
	}
	
}
