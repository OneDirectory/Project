package ie.dit.onedirectory.services.ejbs;

import java.util.Collection;

import ie.dit.onedirectory.dao.FailureClassDAO;
import ie.dit.onedirectory.entities.FailureClass;
import ie.dit.onedirectory.services.FailureClassServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class FailureClassServiceLocalEJB implements FailureClassServiceLocal {

	@EJB
	private FailureClassDAO dao;

	public void setDao(FailureClassDAO dao) {
		this.dao = dao;
	}

	public Collection<FailureClass> getAllFailureClasses() {
		return dao.getAllFailureClasses();
	}

	public void addFailureClass(FailureClass failureClass) {
		dao.addFailureClass(failureClass);
	}

}
