/**
 * 
 * This class allows us to use an entity manager to link
 * our entity objects with the database objects and return data
 * from our queries.
 * 
 * 
 */
package ie.dit.onedirectory.dao.jpa;

import ie.dit.onedirectory.dao.UserDAO;
import ie.dit.onedirectory.entities.User;
import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

	/**
	 * 
	 * @return Queries the DB to return all records from the table
	 * 
	 */
	public Collection<User> getUserList() {
		Query query = em.createQuery("from User");
		return query.getResultList();
	}

	/**
	 * 
	 * Uses the entity manager to store the user to the DB
	 * 
	 */
	public void addUser(User user) {
		em.persist(user);

	}

	/**
	 * Builds the criteria using the user entity and returns a user if the ID is
	 * matched in the DB. Returns a null object if as expected the user may not
	 * actually exist yet when the query returns an empty list.
	 * 
	 */
	public User findByID(Integer id) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> user = criteria.from(User.class);
		criteria.select(user).where(cb.equal(user.get("userID"), id));

		User nullUser = null;

		try {
			return em.createQuery(criteria).getSingleResult();
		} catch (NoResultException nre) {
			// Expected exception when adding to the DB
			return nullUser;
		}

	}

}
