package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.MarketOperator;
import ie.dit.onedirectory.services.MarketOperatorServiceLocal;
import ie.dit.onedirectory.utilities.FileUploadForm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
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
				service.addMarketOperator(new MarketOperator(marketId, operatorId, country, operatorName));
			}
		}
		fis.close();
	}
	
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public void uploadEventCauses(@MultipartForm FileUploadForm form) throws IOException{
		HSSFRow row;
		ByteArrayInputStream stream = new ByteArrayInputStream(form.getFileData());
		HSSFWorkbook workbook = new HSSFWorkbook(stream);
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
				service.addMarketOperator(new MarketOperator(marketId, operatorId, country, operatorName));
			}
		}
		workbook.close();
		stream.close();
	}
	
}
