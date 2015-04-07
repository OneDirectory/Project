/**
 * User dao interface
 * Provides the link to the service layer
 * Returns a collection of all users
 * returns a user by a given id
 * add a user
 * 
 */
package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.User;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface UserDAO {

	public Collection<User> getUserList();

	public void addUser(User user);

	public User findByID(Integer id);

}
