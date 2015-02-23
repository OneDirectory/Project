package ie.dit.onedirectory.services;

import ie.dit.onedirectory.entities.User;

import java.util.Collection;

import javax.ejb.Local;


@Local
public interface UserServiceLocal {

	public Collection<User> getUserList();
	
	public void addUser(User user);
}
