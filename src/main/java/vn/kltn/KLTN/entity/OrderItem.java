package vn.kltn.KLTN.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItem extends Item {
	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private Order order;

	public OrderItem() {
	}

	public OrderItem(String itemId, int price, int quantity, String size, String productId, String productImage) {
		super(itemId, price, quantity, size, productId, productImage);
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
