package ie.dit.onedirectory.entities;

import ie.dit.onedirectory.entities.pks.MarketOperatorId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@IdClass(MarketOperatorId.class)
@Entity
@Table(name = "market_operator")
public class MarketOperator { 
	@Id
	@Column(name = "market_id")
	private Integer marketId;
	@Id
	@Column(name = "operator_id")
	private Integer operatorId;
	@Column(name = "country")
	private String country;
	@Column(name = "operator_name")
	private String operatorName;
	
	public MarketOperator(){
	}
	
	public MarketOperator(Integer marketId, Integer operatorId, String country, String operatorName){
		this.marketId = marketId;
		this.operatorId = operatorId;
		this.country = country;
		this.operatorName = operatorName;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
