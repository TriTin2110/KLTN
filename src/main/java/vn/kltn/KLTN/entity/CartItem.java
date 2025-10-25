package vn.kltn.KLTN.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CartItem {
	@Id
	private String itemId;
	private int price, quantity;
	private String size, productId, productImage;

	public CartItem() {
	}

	public CartItem(String itemId, int price, int quantity, String size, String productId, String productImage) {
		this.itemId = itemId;
		this.price = price;
		this.quantity = quantity;
		this.size = size;
		this.productId = productId;
		this.productImage = productImage;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public void setData(CartItem otherCartItem) {
		this.quantity = otherCartItem.quantity;
		this.size = otherCartItem.size;
	}
}
