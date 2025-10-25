package vn.kltn.KLTN.model;

import java.util.List;

import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.OrderItem;

public class OrderDetailDTO {
	private Order order;
	private List<OrderItem> orderItems;

	public OrderDetailDTO() {
	}

	public OrderDetailDTO(Order order, List<OrderItem> orderItems) {
		this.order = order;
		this.orderItems = orderItems;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

}
