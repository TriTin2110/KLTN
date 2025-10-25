package vn.kltn.KLTN.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Item {
	@Id
	private String itemId;
	private int price, quantity;
	private String size, productId, productImage;

	public Item() {
	}

	public Item(String itemId, int price, int quantity, String size, String productId, String productImage) {
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

}
