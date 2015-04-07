/**
 * Market operator data access class implements the market operator interface
 */
package ie.dit.onedirectory.dao.jpa;

import ie.dit.onedirectory.dao.MarketOperatorDAO;
import ie.dit.onedirectory.entities.EventCause;
import ie.dit.onedirectory.entities.MarketOperator;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local
public class JPAMarketOperatorDAO implements MarketOperatorDAO{
	
	@PersistenceContext
	EntityManager entityManager;

	/**
	 * Returns all market operators using entity manager
	 */
	public Collection<MarketOperator> getAllMarketOperators() {
		Query query = entityManager.createQuery("from MarketOperator");
		return query.getResultList();
	}
	
	/**
	 * Adds a market operator passed pojo if not already exists
	 */

	public void addMarketOperator(MarketOperator marketOperator) {
		if(!entityManager.contains(marketOperator)){
			entityManager.persist(marketOperator);
		}
	}
	
}
