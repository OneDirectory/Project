package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.UserEquipment;
import ie.dit.onedirectory.services.UserEquipmentServiceLocal;
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

@Path("/userequipment")
public class UserEquipmentREST {


	@EJB
	UserEquipmentServiceLocal service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UserEquipment> getAllUserEquipment(){
		return service.getAllUserEquipment();
	}
	
	@GET
	@Path("/{getAllModelsFromUserEquipment}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UserEquipment> getAllModelsFromUserEquipment(){
		return service.getAllModelsFromUserEquipment();		
	}
	
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public Response uploadFailureClasses(@MultipartForm FileUploadForm form) throws IOException {
		HSSFRow row;
		ByteArrayInputStream stream = new ByteArrayInputStream(form.getFileData());
		HSSFWorkbook workbook = new HSSFWorkbook(stream);
		HSSFSheet sheet = workbook.getSheetAt(3);
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			row = (HSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while(cellIterator.hasNext()) {
				DataFormatter dataFormatter = new DataFormatter();
				Integer tac = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
				String marketingName = dataFormatter.formatCellValue(cellIterator.next());
				String manufacturer = dataFormatter.formatCellValue(cellIterator.next());
				String accessCapability = dataFormatter.formatCellValue(cellIterator.next());
				String model = dataFormatter.formatCellValue(cellIterator.next());
				String vendorName = dataFormatter.formatCellValue(cellIterator.next());
				String ueType = dataFormatter.formatCellValue(cellIterator.next());
				String os = dataFormatter.formatCellValue(cellIterator.next());
				String inputMode = dataFormatter.formatCellValue(cellIterator.next());
				service.addUserEquipment(new UserEquipment(tac, marketingName, manufacturer, 
						accessCapability, model, vendorName, ueType, os, inputMode));
			}
		}
		workbook.close();
		stream.close();
		return Response.status(200).entity("Data successfully imported.\n").build();
	}
	
}
