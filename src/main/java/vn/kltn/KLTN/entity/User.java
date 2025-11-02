package vn.kltn.KLTN.entity;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
	@Column(name = "user_name", nullable = false)
	private String username;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "email", nullable = false)
	private String email;
	private String address, fullName, phoneNumber;
	private Date dateOfBirth;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE })
	private List<Comment> comments;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id")
	private Cart cart;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "point_id")
	private Point point;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "chat_room_id")
	private Chat chat;

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, String email, String address, String phoneNumber, Role role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public User(String username, String password, String email, String address, String fullName, Date dateOfBirth,
			List<Comment> comments, Role role, Cart cart, Point point) {
		this.username = username;
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
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
//		cart.setUser(this);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	@Override
	public String toString() {
		return "User [userName=" + username + ", password=" + password + ", email=" + email + ", address=" + address
				+ ", fullName=" + fullName + ", dateOfBirth=" + dateOfBirth + ", role=" + role + ", phone="
				+ phoneNumber + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority(this.role.getType().name()));
	}

	public void addComment(Comment comment) {
		this.comments.add(comment);
		comment.setUser(this);
	}

}
