package vn.kltn.KLTN.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import vn.kltn.KLTN.enums.RoleAvailable;

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(value = EnumType.STRING)
	private RoleAvailable type;
	@OneToMany(mappedBy = "role")
	private List<UserDetail> userDetails;

	public Role() {
	}

	public Role(RoleAvailable type, List<UserDetail> userDetails) {
		this.type = type;
		this.userDetails = userDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RoleAvailable getType() {
		return type;
	}

	public void setType(RoleAvailable type) {
		this.type = type;
	}

	public List<UserDetail> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(List<UserDetail> userDetails) {
		this.userDetails = userDetails;
	}

}
