package ie.dit.onedirectory.rest;

import java.util.Collection;

import ie.dit.onedirectory.entities.CellHierarchy;
import ie.dit.onedirectory.services.CellHierarchyServiceLocal;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Unfinished. Not required!
 * @author Peter
 *
 */

@Path("/cellhierarchy")
public class CellHierarchyREST {

	@EJB
	CellHierarchyServiceLocal service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<CellHierarchy> getAllCellHierarchies(){
		return service.getAllCellHierarchies();
	}
	
	@GET
	@Path("/add")
	public void addCellHierarchies() {
		
	}
}
