package vn.kltn.KLTN.entity;

import jakarta.persistence.Entity;

@Entity
public class OrderItem extends Item {
	public OrderItem() {
	}

	public OrderItem(String itemId, int price, int quantity, String size, String productId, String productImage) {
		super(itemId, price, quantity, size, productId, productImage);
	}
}
