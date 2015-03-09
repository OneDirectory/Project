package ie.dit.onedirectory.entities;

import ie.dit.onedirectory.entities.pks.EventCauseId;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@IdClass(EventCauseId.class)
@Entity
@Table(name = "event_cause")
public class EventCause implements Serializable {
	
	// This is a comment which I will delete later. : D
	
	@Id
	@Column(name="event_id")
	private Integer eventId;
	
	@Id
	@Column(name="cause_code")
	private Integer causeCode;
	
	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy="eventCause")
	private List<FailedCallData> failedCallData;
	
	public EventCause(){
	}
	
	public EventCause(Integer causeCode, Integer eventId, String description){
		this.eventId = eventId;
		this.causeCode = causeCode;
		this.description = description;
	}
	
	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(Integer causeCode) {
		this.causeCode = causeCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		failedCallData.setEventCause(this);

		return failedCallData;
	}

	public FailedCallData removeFailedCallData(FailedCallData failedCallData) {
		getFailedCallData().remove(failedCallData);
		failedCallData.setEventCause(null);

		return failedCallData;
	}

}
