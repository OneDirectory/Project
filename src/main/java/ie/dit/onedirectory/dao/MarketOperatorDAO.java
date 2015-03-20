package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.MarketOperator;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface MarketOperatorDAO {
	
	Collection<MarketOperator> getAllMarketOperators();
	public void addMarketOperator(MarketOperator marketOperator);
}
