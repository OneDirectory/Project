package ie.dit.onedirectory.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="failure_class")
public class FailureClass {

	@Id
	@Column(name="failure_id")
	private Integer failureId;
	
	@Column(name="description")
	private String description;
	
	@OneToMany(mappedBy="failureClass")
	private List<FailedCallData> failedCallData;
	
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

	@JsonIgnore
	public List<FailedCallData> getFailedCallData() {
		return failedCallData;
	}

	public void setFailedCallData(List<FailedCallData> failedCallData) {
		this.failedCallData = failedCallData;
	}
	
	public FailedCallData addFailedCallData(FailedCallData failedCallData) {
		getFailedCallData().add(failedCallData);
		failedCallData.setFailureClass(this);

		return failedCallData;
	}

	public FailedCallData removeFailedCallData(FailedCallData failedCallData) {
		getFailedCallData().remove(failedCallData);
		failedCallData.setFailureClass(null);

		return failedCallData;
	}
	
	
}
