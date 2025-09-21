package vn.kltn.KLTN.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
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

	@Column(name = "type", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private RoleAvailable type;
	@OneToMany(mappedBy = "role")
	private List<User> user;

	public Role() {
	}

	public Role(RoleAvailable type) {
		this.type = type;
		this.user = new ArrayList<User>();
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

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", type=" + type + "]";
	}

}
