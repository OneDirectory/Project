/**
 * Failure class interface
 * returns a collection of all failure classes
 * Add a failure class
 * 
 */
package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.FailureClass;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface FailureClassDAO {
	
	public Collection<FailureClass> getAllFailureClasses();
	public void addFailureClass(FailureClass failureClass);
}
