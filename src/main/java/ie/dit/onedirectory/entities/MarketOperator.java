package ie.dit.onedirectory.entities;

import ie.dit.onedirectory.entities.pks.MarketOperatorId;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement
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
	@OneToMany(mappedBy = "marketOperator")
	private List<FailedCallData> failedCallData;
	
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

	@JsonIgnore
	public List<FailedCallData> getFailedCallData() {
		return this.failedCallData;
	}

	public void setFailedCallData(List<FailedCallData> failedCallData) {
		this.failedCallData = failedCallData;
	}

	public FailedCallData addFailedCallData(FailedCallData failedCallData) {
		getFailedCallData().add(failedCallData);
		failedCallData.setMarketOperator(this);

		return failedCallData;
	}

	public FailedCallData removeFailedCallData(FailedCallData failedCallData) {
		getFailedCallData().remove(failedCallData);
		failedCallData.setMarketOperator(null);

		return failedCallData;
	}
	
}
