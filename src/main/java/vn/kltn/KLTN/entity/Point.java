package vn.kltn.KLTN.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import vn.kltn.KLTN.enums.Rank;

@Entity
public class Point {
	@Id
	private String id;
	private int totalSpent, accumulatedPoint;
	@Enumerated(value = EnumType.STRING)
	private Rank userRank;
	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;
	@OneToOne
	@JoinColumn(name = "user_name")
	private User user;

	public Point() {
	}

	public Point(String id) {
		this.id = id;
		this.totalSpent = 0;
		this.accumulatedPoint = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(int totalSpent) {
		this.totalSpent = totalSpent;
	}

	public int getAccumulatedPoint() {
		return accumulatedPoint;
	}

	public void setAccumulatedPoint(int accumulatedPoint) {
		this.accumulatedPoint = accumulatedPoint;
	}

	public Rank getUserRank() {
		return userRank;
	}

	public void setUserRank(Rank userRank) {
		this.userRank = userRank;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void addOrder(Order order) {
		this.order = order;
		order.setPoint(this);
	}

	public void addUser(User user) {
		this.user = user;
		user.setPoint(this);
	}
}
