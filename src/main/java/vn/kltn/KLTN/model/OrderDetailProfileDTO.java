package vn.kltn.KLTN.model;

import java.sql.Date;
import java.util.List;

import vn.kltn.KLTN.entity.OrderItem;
import vn.kltn.KLTN.enums.OrderStatus;

public class OrderDetailProfileDTO {
	private String id;
	private Date createdDate;
	private int totalPrice;
	private OrderStatus status;
	private List<OrderItem> orderItem;

	public OrderDetailProfileDTO() {
	}

	public OrderDetailProfileDTO(String id, Date createdDate, int totalPrice, OrderStatus status) {
		this.id = id;
		this.createdDate = createdDate;
		this.totalPrice = totalPrice;
		this.status = status;
		this.orderItem = orderItem;
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

	public List<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(List<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}

}
