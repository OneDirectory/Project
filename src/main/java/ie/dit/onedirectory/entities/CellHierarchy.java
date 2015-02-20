package ie.dit.onedirectory.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cell_hierarchy")
public class CellHierarchy {

	@Id
	@Column(name="cell_id")
	private Integer cellId;
	
	@Column(name="hier3_id")
	private String hier3Id;
	
	@Column(name="hier32_id")
	private String hier32Id;
	
	@Column(name="hier321_id")
	private String hier321Id;
	
	public CellHierarchy() {
	}

	public CellHierarchy(Integer cellId, String hier3Id, String hier32Id,
			String hier321Id) {
		this.cellId = cellId;
		this.hier3Id = hier3Id;
		this.hier32Id = hier32Id;
		this.hier321Id = hier321Id;
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
