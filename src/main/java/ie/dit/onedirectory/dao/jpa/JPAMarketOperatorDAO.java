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

	public Collection<MarketOperator> getAllMarketOperators() {
		Query query = entityManager.createQuery("from MarketOperator");
		return query.getResultList();
	}

	public void addMarketOperator(MarketOperator marketOperator) {
		if(!entityManager.contains(marketOperator)){
			entityManager.persist(marketOperator);
		}
	}
	
}
