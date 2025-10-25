package vn.kltn.KLTN.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import vn.kltn.KLTN.enums.OrderStatus;

@Entity
@Table(name = "order_receipt")
public class Order {
	@Id
	private String id;
	private Date createdDate;
	private int totalPrice;
	private String address;
	private String phoneNumber;

	@Enumerated(value = EnumType.STRING)
	private OrderStatus status;
	@OneToOne(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Cart cart;

	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "point_id")
	private Point point;

	@OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
	private List<OrderItem> orderItem;

	public Order() {
	}

	public Order(String id) {
		this.id = id;
		this.createdDate = null;
		this.totalPrice = 0;
		this.status = null;
		this.orderItem = new ArrayList<OrderItem>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(List<OrderItem> orderItem) {
		orderItem.stream().forEach(o -> o.setOrder(this));
		this.orderItem = orderItem;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", createdDate=" + createdDate + ", totalPrice=" + totalPrice + ", status=" + status
				+ ", OrderItem=" + orderItem + "]";
	}

}
