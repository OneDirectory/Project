package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.EventCause;
import ie.dit.onedirectory.services.EventCauseServiceLocal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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
	
	public void addEventCauses(Collection<EventCause> eventCauseList){
		
	}
	
}
