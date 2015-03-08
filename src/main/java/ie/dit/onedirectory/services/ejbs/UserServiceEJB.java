/**
 * Provides a layer of separation between the DAO
 * and the web service API.
 * A DAO interface is injected to provide access to the DAO
 * layer and each method returns data to the REST service API
 * 
 */
package ie.dit.onedirectory.services.ejbs;

import ie.dit.onedirectory.dao.UserDAO;
import ie.dit.onedirectory.entities.User;
import ie.dit.onedirectory.services.UserServiceLocal;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class UserServiceEJB implements UserServiceLocal {

	@EJB
	private UserDAO dao;

	public Collection<User> getUserList() {
		return dao.getUserList();
	}

	public void addUser(User user) {
		dao.addUser(user);

	}

	public User findByID(Integer id) {
		return dao.findByID(id);
	}

}
