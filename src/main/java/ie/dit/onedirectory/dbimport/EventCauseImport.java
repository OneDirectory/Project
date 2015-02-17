package ie.dit.onedirectory.dbimport;

import ie.dit.onedirectory.entities.EventCause;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

public class EventCauseImport {

	static HSSFRow row;
	private static ArrayList<EventCause> eventCauseList;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public EventCauseImport() throws IOException{
		eventCauseList = new ArrayList<EventCause>();
		FileInputStream fis = new FileInputStream(new File("../../Project/data.xls"));
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
				eventCauseList.add(eventCause);
				entityManager.persist(eventCause);
			}
		}
		System.out.println(eventCauseList.size());
		fis.close();
	}
	
	public static void main(String[] args) throws Exception {
		new EventCauseImport();
	}

}
