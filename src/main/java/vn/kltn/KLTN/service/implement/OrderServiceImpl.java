package vn.kltn.KLTN.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.OrderItem;
import vn.kltn.KLTN.enums.OrderStatus;
import vn.kltn.KLTN.enums.Payment;
import vn.kltn.KLTN.model.OrderDetailDTO;
import vn.kltn.KLTN.repository.OrderRepository;
import vn.kltn.KLTN.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository repository;

	@Override
	@Transactional
	public List<Order> checkingAll(String userName) {
		// TODO Auto-generated method stub
		List<Order> orders = repository.findByUsername(userName);
//		for (Order order : orders) {
//			ListorderedItem = new HashMap<Product, Integer>();
//			orderedItem.putAll(order.getOrderItem());
//			order.setOrderItem(orderedItem);
//		}
		return orders;
	}

	@Override
	public Order choosePaymentMethod(Payment choice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order checkCurrentOrder(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public boolean add(Order order) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Bắt đầu cập nhật");
			repository.saveAndFlush(order);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public boolean remove(String orderId) {
		// TODO Auto-generated method stub
		try {
			Optional<Order> opt = repository.findById(orderId);
			if (opt.isPresent()) {
				Order order = opt.get();
				repository.delete(order);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public boolean update(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public OrderDetailDTO findById(String orderId) {
		// TODO Auto-generated method stub
		Optional<Order> opt = repository.findById(orderId);
		if (opt.isEmpty())
			return null;
		else {
			Order order = opt.get();
			List<OrderItem> orderItems = new ArrayList<OrderItem>();
			orderItems.addAll(order.getOrderItem());
			OrderDetailDTO orderDetailDTO = new OrderDetailDTO(order, orderItems);
			return orderDetailDTO;
		}
	}

	@Override
	public List<Order> findAll() {
		// TODO Auto-generated method stub
		List<Order> orders = repository.findAll();
		return orders;
	}

	@Override
	public List<Order> findAllWithItems() {
		// TODO Auto-generated method stub
		return repository.findAllWithItems();
	}

	@Override
	@Transactional
	public int updateStatus(String id, String status) {
		// TODO Auto-generated method stub
		return this.repository.updateStatus(id, OrderStatus.valueOf(status));
	}

}
