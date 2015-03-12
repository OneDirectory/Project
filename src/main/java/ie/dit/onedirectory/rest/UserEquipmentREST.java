package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.UserEquipment;
import ie.dit.onedirectory.services.UserEquipmentServiceLocal;

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
	@Path("/add")
	public void addFailureClasses() throws IOException {
		HSSFRow row;
	//	FileInputStream fis = new FileInputStream(new File("C:/oneDirectory/data.xls"));
		FileInputStream fis = new FileInputStream(new File("/home/drrn/Project/data.xls"));
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
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
		fis.close();
		
	}
	
}
