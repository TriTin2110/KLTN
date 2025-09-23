package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.enums.Payment;

public interface OrderService {
	public List<Order> checkingAll(String userName);

	public Order choosePaymentMethod(Payment choice);

	public Order checkCurrentOrder(String userName);

	public boolean add(Order order);

	public boolean remove(String orderId);

	public boolean update(Order order);

	public Order findById(String orderId);

	public List<Order> findAll();
}
