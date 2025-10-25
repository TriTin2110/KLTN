package vn.kltn.KLTN.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import vn.kltn.KLTN.enums.Rank;

@Entity
public class Point {
	@Id
	private String id;
	private int totalSpent, accumulatedPoint;
	@Enumerated(value = EnumType.STRING)
	private Rank userRank;
	@OneToMany(mappedBy = "point", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private List<Order> orders;
//	@OneToOne
//	@JoinColumn(name = "user_name")
//	private User user;

	public Point() {
	}

	public Point(String id) {
		this.id = id;
		this.totalSpent = 0;
		this.accumulatedPoint = 0;
		this.orders = new ArrayList<Order>();
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

	public List<Order> getOrder() {
		return orders;
	}

	public void setOrder(List<Order> orders) {
		this.orders = orders;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

//	public void addOrder(Order orders) {
//		this.orders = orders;
//		orders.setPoint(this);
//	}

//	public void addUser(User user) {
//		this.user = user;
//		user.setPoint(this);
//	}

//	@Override
//	public String toString() {
//		return "Point [id=" + id + ", totalSpent=" + totalSpent + ", accumulatedPoint=" + accumulatedPoint
//				+ ", userRank=" + userRank + "]";
//	}

}
