package ie.dit.onedirectory.dao.jpa;

import ie.dit.onedirectory.dao.UserDAO;
import ie.dit.onedirectory.entities.User;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
@Local
public class UserDAOImplemetation implements UserDAO {

	@PersistenceContext
	private EntityManager em;

	public Collection<User> getUserList() {
		Query query = em.createQuery("from User");
		return query.getResultList();
	}

	public void addUser(User user) {
		em.persist(user);

	}

}
