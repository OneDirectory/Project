package ie.dit.onedirectory.entities;

import ie.dit.onedirectory.entities.pks.EventCauseId;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="failed_call_data")
public class FailedCallData {

//	@Column(name="date_time")
//	private Date dateTime;
//	
//	@Column(name="event_id")
//	private Integer eventId;
//	
//	@Column(name="failure_id")
//	private Integer failureId;
//	
//	@Column(name="type_allocation_code")
//	private Integer typeAllocationCode;
//	
//	@Column(name="market_id")
//	private Integer marketId;
//	
//	@Column(name="operator_id")
//	private Integer operatorId;
//	
//	@Column(name="cell_id")
//	private Integer cellId;
//	
//	@Column(name="duration")
//	private Integer duration;
//	
//	@Column(name="cause_code")
//	private Integer causeCode;
//	
//	@Column(name="network_element_version")
//	private String networkElementVersion;
//	
//	@Column(name="imsi")
//	private String imsi;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_time")
	private Date dateTime;

	@Column(name="cell_id")
	private Integer cellId;
	
	private Integer duration;

	private String imsi;

	@Column(name="hier3_id")
	private String hier3Id;

	@Column(name="hier32_id")
	private String hier32Id;

	@Column(name="hier321_id")
	private String hier321Id;
	
	@Column(name="network_element_version")
	private String networkElementVersion;

	//bi-directional many-to-one association to EventCause
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="cause_code", referencedColumnName="cause_code"),
		@JoinColumn(name="event_id", referencedColumnName="event_id")
		})
	private EventCause eventCause;

	//bi-directional many-to-one association to FailureClass
	@ManyToOne
	@JoinColumn(name="failure_id")
	private FailureClass failureClass;

	//bi-directional many-to-one association to UserEquipment
	@ManyToOne
	@JoinColumn(name="type_allocation_code")
	private UserEquipment userEquipment;

	//bi-directional many-to-one association to MarketOperator
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="market_id", referencedColumnName="market_id"),
		@JoinColumn(name="operator_id", referencedColumnName="operator_id")
		})
	private MarketOperator marketOperator;

	
	
	public FailedCallData() {
		
	}

	public FailedCallData(Date dateTime, Integer eventId,
			Integer failureId, Integer typeAllocationCode, Integer marketId,
			Integer operatorId, Integer cellId, Integer duration,
			Integer causeCode, String networkElementVersion, String imsi, 
			String hier3Id, String hier32Id, String hier321Id) {
		this.dateTime = dateTime;
		this.eventCause = new EventCause();
		this.eventCause.setEventId(eventId);
		this.eventCause.setCauseCode(causeCode);
		this.failureClass = new FailureClass();
		this.failureClass.setFailureId(failureId);
		this.userEquipment = new UserEquipment();
		this.userEquipment.setTac(typeAllocationCode);
		this.marketOperator = new MarketOperator();
		this.marketOperator.setMarketId(marketId);
		this.marketOperator.setOperatorId(operatorId);
		this.cellId = cellId;
		this.duration = duration;
		this.networkElementVersion = networkElementVersion;
		this.imsi = imsi;
		this.hier3Id = hier3Id;
		this.hier32Id = hier32Id;
		this.hier321Id = hier321Id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getImsi() {
		return this.imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getNetworkElementVersion() {
		return this.networkElementVersion;
	}

	public void setNetworkElementVersion(String networkElementVersion) {
		this.networkElementVersion = networkElementVersion;
	}

	public EventCause getEventCause() {
		return this.eventCause;
	}

	public void setEventCause(EventCause eventCause) {
		this.eventCause = eventCause;
	}

	public FailureClass getFailureClass() {
		return this.failureClass;
	}

	public void setFailureClass(FailureClass failureClass) {
		this.failureClass = failureClass;
	}

	public UserEquipment getUserEquipment() {
		return this.userEquipment;
	}

	public void setUserEquipment(UserEquipment userEquipment) {
		this.userEquipment = userEquipment;
	}

	public MarketOperator getMarketOperator() {
		return this.marketOperator;
	}

	public void setMarketOperator(MarketOperator marketOperator) {
		this.marketOperator = marketOperator;
	}

	public Integer getCellId() {
		return cellId;
	}

	public void setCellId(Integer cellId) {
		this.cellId = cellId;
	}

	public String getHier3Id() {
		return hier3Id;
	}

	public void setHier3Id(String hier3Id) {
		this.hier3Id = hier3Id;
	}

	public String getHier32Id() {
		return hier32Id;
	}

	public void setHier32Id(String hier32Id) {
		this.hier32Id = hier32Id;
	}

	public String getHier321Id() {
		return hier321Id;
	}

	public void setHier321Id(String hier321Id) {
		this.hier321Id = hier321Id;
	}

	
	
}
