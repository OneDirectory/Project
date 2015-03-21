package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.FailureClass;
import ie.dit.onedirectory.services.FailureClassServiceLocal;

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

@Path("/failureclasses")
public class FailureClassREST {

	@EJB
	FailureClassServiceLocal service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailureClass> getAllFailureClasses(){
		return service.getAllFailureClasses();
	}
	
	@GET
	@Path("/add")
	public void addFailureClasses() throws IOException {
		HSSFRow row;
		FileInputStream fis = new FileInputStream(new File("/home/drrn/Project/data.xls"));
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.getSheetAt(2);
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			row = (HSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while(cellIterator.hasNext()) {
				DataFormatter dataFormatter = new DataFormatter();
				Integer failureId = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
				String description = dataFormatter.formatCellValue(cellIterator.next());
				service.addFailureClass(new FailureClass(failureId, description));
			}
		}
		fis.close();
		
	}

}
