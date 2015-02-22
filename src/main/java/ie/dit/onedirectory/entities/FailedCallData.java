package ie.dit.onedirectory.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="failed_call_data")
public class FailedCallData {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="date_time")
	private Date dateTime;
	
	@Column(name="event_id")
	private Integer eventId;
	
	@Column(name="failure_id")
	private Integer failureId;
	
	@Column(name="type_allocation_code")
	private Integer typeAllocationCode;
	
	@Column(name="market_id")
	private Integer marketId;
	
	@Column(name="operator_id")
	private Integer operatorId;
	
	@Column(name="cell_id")
	private Integer cellId;
	
	@Column(name="duration")
	private Integer duration;
	
	@Column(name="cause_code")
	private Integer causeCode;
	
	@Column(name="network_element_version")
	private String networkElementVersion;
	
	@Column(name="imsi")
	private String imsi;
	
	public FailedCallData() {
	}

	public FailedCallData(Date dateTime, Integer eventId,
			Integer failureId, Integer typeAllocationCode, Integer marketId,
			Integer operatorId, Integer cellId, Integer duration,
			Integer causeCode, String networkElementVersion, String imsi) {
		this.dateTime = dateTime;
		this.eventId = eventId;
		this.failureId = failureId;
		this.typeAllocationCode = typeAllocationCode;
		this.marketId = marketId;
		this.operatorId = operatorId;
		this.cellId = cellId;
		this.duration = duration;
		this.causeCode = causeCode;
		this.networkElementVersion = networkElementVersion;
		this.imsi = imsi;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getFailureId() {
		return failureId;
	}

	public void setFailureId(Integer failureId) {
		this.failureId = failureId;
	}

	public Integer getTypeAllocationCode() {
		return typeAllocationCode;
	}

	public void setTypeAllocationCode(Integer typeAllocationCode) {
		this.typeAllocationCode = typeAllocationCode;
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

	public Integer getCellId() {
		return cellId;
	}

	public void setCellId(Integer cellId) {
		this.cellId = cellId;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(Integer causeCode) {
		this.causeCode = causeCode;
	}

	public String getNetworkElementVersion() {
		return networkElementVersion;
	}

	public void setNetworkElementVersion(String networkElementVersion) {
		this.networkElementVersion = networkElementVersion;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
}
