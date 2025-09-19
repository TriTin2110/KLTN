package vn.kltn.KLTN.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {
	@Id
	private String userName;
	private String address, fullName;

	@OneToMany(mappedBy = "user")
	private List<Comment> comments;
	@OneToOne
	@JoinColumn(name = "user_detail_id")
	private UserDetail userDetail;
	@OneToOne(mappedBy = "user")
	private Cart cart;
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	@OneToOne(mappedBy = "user")
	private Point point;

	public User() {
	}

	public User(String userName, String address, String fullName, List<Comment> comments, UserDetail userDetail,
			Cart cart, List<Order> orders, Point point) {
		this.userName = userName;
		this.address = address;
		this.fullName = fullName;
		this.comments = comments;
		this.userDetail = userDetail;
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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
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

}
