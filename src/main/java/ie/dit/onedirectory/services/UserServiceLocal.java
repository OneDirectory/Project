/**
 * 
 * Provides the link between to the service layer
 * from the RESTful service API.
 * Defines methods to return all users, a specific
 * user by his ID and adding a new user to the database.
 * 
 */
package ie.dit.onedirectory.services;

import ie.dit.onedirectory.entities.User;

import java.util.Collection;

import javax.ejb.Local;


@Local
public interface UserServiceLocal {

	public Collection<User> getUserList();
	
	public void addUser(User user);
	
	public User findByID(Integer id);
}
