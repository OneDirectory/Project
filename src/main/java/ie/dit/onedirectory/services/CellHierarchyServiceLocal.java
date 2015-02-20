package ie.dit.onedirectory.services;

import ie.dit.onedirectory.entities.CellHierarchy;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface CellHierarchyServiceLocal {

	public Collection<CellHierarchy> getAllCellHierarchies();
	public void addCellHierarchy(CellHierarchy cellHierarchy);
	public void addCellHierarchies(Collection<CellHierarchy> cellHierarchyList);
	
}
