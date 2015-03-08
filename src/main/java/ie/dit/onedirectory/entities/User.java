/**
 * 
 * Our user class entity consisting of an ID, first and second name
 * password and user type/role. User ID is the primary key and no field 
 * can be null with a password having a 4-6 character spec.
 * 
 */
package ie.dit.onedirectory.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "user_table")
public class User implements Serializable {

	public User(int userID, String userType, String userPassword,
			String userFName, String userSName) {
		this.userID = userID;
		this.userType = userType;
		this.userFName = userFName;
		this.userSName = userSName;
		this.userPassword = userPassword;

	}

	public User() {

	}

	@Id
	@Column(name = "user_id")
	private int userID;

	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "user_type")
	private String userType;

	@NotNull
	@Size(min = 4, max = 6)
	@Column(name = "user_password")
	private String userPassword;

	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "first_name")
	private String userFName;

	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "second_name")
	private String userSName;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserFName() {
		return userFName;
	}

	public void setUserFName(String userFName) {
		this.userFName = userFName;
	}

	public String getUserSName() {
		return userSName;
	}

	public void setUserSName(String userSName) {
		this.userSName = userSName;
	}

}
