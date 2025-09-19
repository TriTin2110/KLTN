package vn.kltn.KLTN.entity;

import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
	@Id
	private String id;
	private short totalOrder;

	@OneToOne
	@JoinColumn(name = "user_name")
	private User user;

	@ElementCollection
	@CollectionTable(name = "items_in_cart", joinColumns = {
			@JoinColumn(name = "cart_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "product_id")
	@Column(name = "quantity")
	private Map<Product, Integer> cartItems;
	@OneToOne(mappedBy = "cart")
	private Order order;

	public Cart() {
	}

	public Cart(String id, short totalOrder, User user, Map<Product, Integer> cartItems, Order order) {
		this.id = id;
		this.totalOrder = totalOrder;
		this.user = user;
		this.cartItems = cartItems;
		this.order = order;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public short getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(short totalOrder) {
		this.totalOrder = totalOrder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<Product, Integer> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Map<Product, Integer> cartItems) {
		this.cartItems = cartItems;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
