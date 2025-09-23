package vn.kltn.KLTN.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {
	@Id
	private String userName;
	private String password, email, address, fullName;
	private Date dateOfBirth;

	@OneToMany(mappedBy = "user")
	private List<Comment> comments;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "role_id")
	private Role role;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Cart cart;
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	@OneToOne(mappedBy = "user")
	private Point point;

	public User() {
	}

	public User(String userName, String password, String email, String address, Role role) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.address = address;
		this.role = role;
	}

	public User(String userName, String password, String email, String address, String fullName, Date dateOfBirth,
			List<Comment> comments, Role role, Cart cart, List<Order> orders, Point point) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.address = address;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.comments = comments;
		this.role = role;
		this.cart = cart;
		this.orders = orders;
		this.point = point;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", email=" + email + ", address=" + address
				+ ", fullName=" + fullName + ", dateOfBirth=" + dateOfBirth + ", role=" + role + "]";
	}

}
