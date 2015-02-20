package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.CellHierarchy;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface CellHierarchyDAO {

	public Collection<CellHierarchy> getAllCellHierarchies();
	public void addCellHierarchy(CellHierarchy cellHierarchy);
	public void addCellHierarchies(Collection<CellHierarchy> cellHierarchyList);
	
}
