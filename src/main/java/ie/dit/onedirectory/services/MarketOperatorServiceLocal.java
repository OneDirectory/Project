package ie.dit.onedirectory.services;

import ie.dit.onedirectory.entities.MarketOperator;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface MarketOperatorServiceLocal {
	public Collection<MarketOperator> getMarketOperators();
	public void addMarketOperator(MarketOperator marketOperator);
}
