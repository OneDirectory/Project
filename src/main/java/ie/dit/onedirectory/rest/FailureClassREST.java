package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.FailureClass;
import ie.dit.onedirectory.services.FailureClassServiceLocal;
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
import javax.ws.rs.core.Response;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/failureclasses")
public class FailureClassREST {

	@EJB
	FailureClassServiceLocal service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailureClass> getAllFailureClasses(){
		return service.getAllFailureClasses();
	}
	
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public Response addFailureClasses(@MultipartForm FileUploadForm form) throws IOException {
		HSSFRow row;
		ByteArrayInputStream stream = new ByteArrayInputStream(form.getFileData());
		HSSFWorkbook workbook = new HSSFWorkbook(stream);
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
		workbook.close();
		stream.close();
		return Response.status(200).entity("Data successfully imported.\n").build();
	}

}
