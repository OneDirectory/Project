package ie.dit.onedirectory.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_equipment")
public class UserEquipment {

	@Id
	@Column(name="type_allocation_code")
	private Integer tac;
	
	@Column(name="marketing_name")
	private String marketingName;
	
	@Column(name="manufacturer")
	private String manufacturer;

	@Column(name="access_capability")
	private String accessCapability;
	
	@Column(name="model")
	private String model;
	
	@Column(name="vendor_name")
	private String vendorName;
	
	@Column(name="ue_type")
	private String ueType;
	
	@Column(name="operating_system")
	private String os;
	
	@Column(name="input_mode")
	private String inputMode;
	
	public UserEquipment() {
	}

	public UserEquipment(Integer tac, String marketingName,
			String manufacturer, String accessCapability, String model,
			String vendorName, String ueType, String os, String inputMode) {
		this.tac = tac;
		this.marketingName = marketingName;
		this.manufacturer = manufacturer;
		this.accessCapability = accessCapability;
		this.model = model;
		this.vendorName = vendorName;
		this.ueType = ueType;
		this.os = os;
		this.inputMode = inputMode;
	}

	public Integer getTac() {
		return tac;
	}

	public void setTac(Integer tac) {
		this.tac = tac;
	}

	public String getMarketingName() {
		return marketingName;
	}

	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getAccessCapability() {
		return accessCapability;
	}

	public void setAccessCapability(String accessCapability) {
		this.accessCapability = accessCapability;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getUeType() {
		return ueType;
	}

	public void setUeType(String ueType) {
		this.ueType = ueType;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getInputMode() {
		return inputMode;
	}

	public void setInputMode(String inputMode) {
		this.inputMode = inputMode;
	}

}
