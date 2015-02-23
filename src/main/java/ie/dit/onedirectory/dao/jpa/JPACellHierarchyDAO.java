package ie.dit.onedirectory.dao.jpa;

import ie.dit.onedirectory.dao.CellHierarchyDAO;
import ie.dit.onedirectory.entities.CellHierarchy;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local
public class JPACellHierarchyDAO implements CellHierarchyDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Collection<CellHierarchy> getAllCellHierarchies() {
		Query q = entityManager.createQuery("select f from FailureClass as f");
		return q.getResultList();
	}

	public void addCellHierarchy(CellHierarchy cellHierarchy) {
		entityManager.persist(cellHierarchy);
	}

	public void addCellHierarchies(Collection<CellHierarchy> cellHierarchyList) {
		entityManager.persist(cellHierarchyList);
	}

}
