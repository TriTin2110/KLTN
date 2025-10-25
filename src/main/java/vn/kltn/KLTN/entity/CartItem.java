package vn.kltn.KLTN.entity;

import jakarta.persistence.Entity;

@Entity
public class CartItem extends Item {
	public CartItem() {
	}

	public CartItem(String itemId, int price, int quantity, String size, String productId, String productImage) {
		super(itemId, price, quantity, size, productId, productImage);
	}

	public void setData(CartItem otherCartItem) {
		super.setQuantity(otherCartItem.getQuantity());
		super.setSize(otherCartItem.getSize());
	}

	public OrderItem convertOrderItem() {
		return new OrderItem(getItemId() + "-" + System.currentTimeMillis(), getPrice(), getQuantity(), getSize(),
				getProductId(), getProductImage());
	}
}
