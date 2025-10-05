package vn.kltn.KLTN.entity;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;
	@Id
	private String userName;
	private String password, email, address, fullName, phoneNumber;
	private Date dateOfBirth;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE })
	private List<Comment> comments;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id")
	private Cart cart;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Point point;

	public User() {
	}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public User(String userName, String password, String email, String address, String phoneNumber, Role role) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public User(String userName, String password, String email, String address, String fullName, Date dateOfBirth,
			List<Comment> comments, Role role, Cart cart, Point point) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.address = address;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.comments = comments;
		this.role = role;
		this.cart = cart;
		this.point = point;
	}

	@Override
	public String getUsername() {
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

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public void addCart(Cart cart) {
		this.cart = cart;
		cart.setUser(this);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", email=" + email + ", address=" + address
				+ ", fullName=" + fullName + ", dateOfBirth=" + dateOfBirth + ", role=" + role + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority(this.role.getType().name()));
	}

}
