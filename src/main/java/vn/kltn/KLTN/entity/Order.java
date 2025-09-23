package vn.kltn.KLTN.entity;

import java.sql.Date;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
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

	@Enumerated(value = EnumType.STRING)
	private OrderStatus status;
	@OneToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	@OneToOne
	@JoinColumn(name = "user_name")
	private User user;

	@ElementCollection
	@CollectionTable(name = "item_ordered", joinColumns = {
			@JoinColumn(name = "order_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "product_id")
	@Column(name = "quantity")
	private Map<Product, Integer> orderItem;

	public Order() {
	}

	public Order(String id, Date createdDate, int totalPrice, OrderStatus status, Cart cart, User user,
			Map<Product, Integer> orderItem) {
		this.id = id;
		this.createdDate = createdDate;
		this.totalPrice = totalPrice;
		this.status = status;
		this.cart = cart;
		this.user = user;
		this.orderItem = orderItem;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<Product, Integer> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(Map<Product, Integer> orderItem) {
		this.orderItem = orderItem;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", createdDate=" + createdDate + ", totalPrice=" + totalPrice + ", status=" + status
				+ ", cart=" + cart + ", user=" + user + ", orderItem=" + orderItem + "]";
	}

}
