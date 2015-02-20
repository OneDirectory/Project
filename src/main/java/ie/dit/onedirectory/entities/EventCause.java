package ie.dit.onedirectory.entities;

import java.io.Serializable;

import ie.dit.onedirectory.entities.pks.EventCauseId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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

}
