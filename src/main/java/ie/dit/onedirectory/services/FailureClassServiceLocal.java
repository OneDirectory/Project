package ie.dit.onedirectory.services;

import ie.dit.onedirectory.entities.FailureClass;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface FailureClassServiceLocal {
	
	public Collection<FailureClass> getAllFailureClasses();
	public void addFailureClass(FailureClass failureClass);
	public void addFailureClasses(Collection<FailureClass> failureClassList);

}
