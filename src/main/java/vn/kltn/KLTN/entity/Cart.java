package vn.kltn.KLTN.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
	@Id
	private String id;
	private int totalPrice;
	// orphanRemoval = true khác với cascade. Nó chuyên dùng để xóa các entity không
	// còn liên kết với parent.
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cart_id")
	private List<CartItem> cartItems;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	public Cart() {
	}

	public Cart(String id) {
		this.id = id;
		this.cartItems = new ArrayList<CartItem>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

//	@Override
//	public String toString() {
//		return "Cart [id=" + id + ", user=" + user + ", cartItems=" + cartItems + "]";
//	}

	public void returnToOriginState() {
		this.cartItems = new ArrayList<CartItem>();
		this.order = null;
	}

	public void addOrder(Order order) {
		this.order = order;
		order.setCart(this);
	}
}
