package vn.kltn.KLTN.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class CartItem {
	@Id
	private int itemId;
	private int price, quantity;
	private String size;

	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	@OneToOne
	private Product product;

	public CartItem() {
	}

	public CartItem(int itemId, int price, int quantity, String size, Cart cart, Product product) {
		this.itemId = itemId;
		this.price = price;
		this.quantity = quantity;
		this.size = size;
		this.cart = cart;
		this.product = product;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setData(CartItem otherCartItem) {
		this.quantity = otherCartItem.quantity;
		this.size = otherCartItem.size;
	}
}
