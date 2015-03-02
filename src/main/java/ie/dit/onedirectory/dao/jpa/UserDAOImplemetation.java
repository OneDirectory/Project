package ie.dit.onedirectory.dao.jpa;

import ie.dit.onedirectory.dao.UserDAO;
import ie.dit.onedirectory.entities.User;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


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
	
	public User findByID(Integer id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> user = criteria.from(User.class);

		criteria.select(user).where(cb.equal(user.get("userID"), id));
		return em.createQuery(criteria).getSingleResult();
	}

}
