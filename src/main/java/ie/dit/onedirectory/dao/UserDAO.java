package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.User;

import java.util.Collection;

import javax.ejb.Local;



@Local
public interface UserDAO {

	public Collection<User> getUserList();

	public void addUser(User user);
}
