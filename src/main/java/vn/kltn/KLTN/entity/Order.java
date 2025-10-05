package vn.kltn.KLTN.entity;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CascadeType;
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
	@OneToOne(mappedBy = "order", cascade = CascadeType.MERGE)
	private Cart cart;

	@OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE)
	private Point point;

	@ElementCollection
	@CollectionTable(name = "item_ordered", joinColumns = {
			@JoinColumn(name = "order_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "product_id")
	@Column(name = "quantity")
	private Map<Product, Integer> orderItem;

	public Order() {
	}

	public Order(String id) {
		this.id = id;
		this.createdDate = null;
		this.totalPrice = 0;
		this.status = null;
		this.orderItem = new HashMap<Product, Integer>();
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

	public Map<Product, Integer> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(Map<Product, Integer> orderItem) {
		this.orderItem = orderItem;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", createdDate=" + createdDate + ", totalPrice=" + totalPrice + ", status=" + status
				+ ", orderItem=" + orderItem + "]";
	}

}
