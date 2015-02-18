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
//	@Consumes(MediaType.APPLICATION_JSON)
	public void addEventCauses() throws IOException{
		HSSFRow row;
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
				EventCause eventCause = new EventCause(causeCode, eventId, description);
				System.out.println(eventCause.getEventId() + "\t" +  eventCause.getCauseCode() + "\t" + eventCause.getDescription());
				service.addEventCause(new EventCause(causeCode, eventId, description));
			}
		}
		fis.close();
	}
	
//	@POST
//	@Path("/add")
//	@Consumes("multipart/form-data")
//	public void addEventCauses(MultipartFormDataInput input){
//		String fileName = "";
//		
//		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
//		List<InputPart> inputParts = uploadForm.get("uploadedFile");
//		
//		for (InputPart inputPart : inputParts) {
//
//            MultivaluedMap<String, String> header = inputPart.getHeaders();
//            fileName = getFileName(header);
//
//            try {
//                HSSFWorkbook workbook;
//                try (FileInputStream fis = inputPart.getBody(FileInputStream.class, null)) {
//                    workbook = new HSSFWorkbook(fis);
//                    HSSFSheet sheet = workbook.getSheetAt(0);
//                    Iterator<Row> rowIterator = sheet.iterator();
//                    rowIterator.next();
//                    while (rowIterator.hasNext()) {
//                        HSSFRow row = (HSSFRow) rowIterator.next();
//                        Iterator<Cell> cellIterator = row.cellIterator();
//                        while (cellIterator.hasNext()) {
//
//                            DataFormatter dataFormatter = new DataFormatter();
//            				Integer causeCode = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
//            				Integer eventId = Integer.valueOf(dataFormatter.formatCellValue(cellIterator.next()));
//            				String description = cellIterator.next().getStringCellValue();
//            				EventCause eventCause = new EventCause(causeCode, eventId, description);
//            				System.out.println(eventCause.getEventId() + "\t" +  eventCause.getCauseCode() + "\t" + eventCause.getDescription());
//            				service.addEventCause(new EventCause(causeCode, eventId, description));
//                        }
//                        System.out.println("");
//                    }
//                }
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//	}
//	
//	private String getFileName(MultivaluedMap<String, String> header) {
//
//        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
//
//        for (String filename : contentDisposition) {
//            if ((filename.trim().startsWith("filename"))) {
//
//                String[] name = filename.split("=");
//
//                String finalFileName = name[1].trim().replaceAll("\"", "");
//                return finalFileName;
//            }
//        }
//        return "unknown";
//    }
	
}
