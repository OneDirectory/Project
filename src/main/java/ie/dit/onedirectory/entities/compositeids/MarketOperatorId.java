package ie.dit.onedirectory.entities.compositeids;

import java.io.Serializable;

public class MarketOperatorId implements Serializable {

	private Integer marketId;
	private Integer operatorId;

	public MarketOperatorId() {
	}

	public MarketOperatorId(Integer marketId, Integer operatorId) {
		this.marketId = marketId;
		this.operatorId = operatorId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((marketId == null) ? 0 : marketId.hashCode());
		result = prime * result
				+ ((operatorId == null) ? 0 : operatorId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarketOperatorId other = (MarketOperatorId) obj;
		if (marketId == null) {
			if (other.marketId != null)
				return false;
		} else if (!marketId.equals(other.marketId))
			return false;
		if (operatorId == null) {
			if (other.operatorId != null)
				return false;
		} else if (!operatorId.equals(other.operatorId))
			return false;
		return true;
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

}
