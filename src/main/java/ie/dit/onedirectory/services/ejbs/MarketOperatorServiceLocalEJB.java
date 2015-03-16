package ie.dit.onedirectory.services.ejbs;

import ie.dit.onedirectory.dao.MarketOperatorDAO;
import ie.dit.onedirectory.entities.MarketOperator;
import ie.dit.onedirectory.services.MarketOperatorServiceLocal;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
@Stateless
public class MarketOperatorServiceLocalEJB implements MarketOperatorServiceLocal {

	@EJB
	private MarketOperatorDAO dao;
	
	public void setDao(MarketOperatorDAO dao){
		this.dao = dao;
	}
	
	public Collection<MarketOperator> getMarketOperators() {
		return dao.getAllMarketOperators();
	}

	public void addMarketOperator(MarketOperator marketOperator) {
		dao.addMarketOperator(marketOperator);
	}
	
}
