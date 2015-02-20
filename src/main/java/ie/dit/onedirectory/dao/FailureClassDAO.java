package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.FailureClass;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface FailureClassDAO {
	
	public Collection<FailureClass> getAllFailureClasses();
	public void addFailureClass(FailureClass failureClass);
	public void addFailureClasses(Collection<FailureClass> failureClassList);

}
