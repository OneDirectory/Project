package ie.dit.onedirectory.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="failure_class")
public class FailureClass {

	@Id
	@Column(name="failure_id")
	private Integer failureId;
	
	@Column(name="description")
	private String description;
	
	public FailureClass() {
	}
	
	public FailureClass(Integer failureId, String description) {
		this.failureId = failureId;
		this.description = description;
	}

	public Integer getFailureId() {
		return failureId;
	}

	public void setFailureId(Integer failureId) {
		this.failureId = failureId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
