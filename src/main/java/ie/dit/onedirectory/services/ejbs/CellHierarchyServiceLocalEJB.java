package ie.dit.onedirectory.services.ejbs;

import java.util.Collection;

import ie.dit.onedirectory.dao.CellHierarchyDAO;
import ie.dit.onedirectory.entities.CellHierarchy;
import ie.dit.onedirectory.services.CellHierarchyServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class CellHierarchyServiceLocalEJB implements CellHierarchyServiceLocal {

	@EJB
	private CellHierarchyDAO dao;
	
	public void setDao(CellHierarchyDAO dao){
		this.dao = dao;
	}

	public Collection<CellHierarchy> getAllCellHierarchies() {
		return dao.getAllCellHierarchies();
	}

	public void addCellHierarchy(CellHierarchy cellHierarchy) {
		dao.addCellHierarchy(cellHierarchy);
	}

	public void addCellHierarchies(Collection<CellHierarchy> cellHierarchyList) {
		dao.addCellHierarchies(cellHierarchyList);
	}
	
}
