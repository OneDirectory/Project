package ie.dit.onedirectory.entities;

import ie.dit.onedirectory.entities.pks.EventCauseId;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@IdClass(EventCauseId.class)
@Entity
@XmlRootElement
@Table(name = "event_cause")
public class EventCause implements Serializable {
	
	@Id
	@Column(name = "cause_code")
	private Integer causeCode;
	@Id
	@Column(name = "event_id")
	private Integer eventId;
	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy="eventCause")
	private List<FailedCallData> failedCallData;
	
	public EventCause(){
	}
	
	public EventCause(Integer causeCode, Integer eventId, String description){
		this.causeCode = causeCode;
		this.eventId = eventId;
		this.description = description;
	}
	
	public Integer getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(Integer causeCode) {
		this.causeCode = causeCode;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<FailedCallData> getFailedCallData() {
		return this.failedCallData;
	}

	public void setFailedCallData(List<FailedCallData> failedCallData) {
		this.failedCallData = failedCallData;
	}

	public FailedCallData addFailedCallData(FailedCallData failedCallData) {
		getFailedCallData().add(failedCallData);
		failedCallData.setEventCause(this);

		return failedCallData;
	}

	public FailedCallData removeFailedCallData(FailedCallData failedCallData) {
		getFailedCallData().remove(failedCallData);
		failedCallData.setEventCause(null);

		return failedCallData;
	}

}
