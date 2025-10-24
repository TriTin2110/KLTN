package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.enums.Payment;
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
//		try {
//			Order order = findById(orderId);
//			order.getPoint().setOrder(null);
//			if (order != null) {
//				order.getOrderItem().clear();
//				order.setPoint(null);
//				repository.delete(order);
//				return true;
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		return false;
	}

	@Override
	@Transactional
	public boolean update(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Order findById(String orderId) {
		// TODO Auto-generated method stub
		Optional<Order> opt = repository.findById(orderId);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	@Transactional
	public List<Order> findAll() {
		// TODO Auto-generated method stub
		List<Order> orders = repository.findAll();
		orders.forEach(o -> System.out.println(o.getOrderItem()));
		return null;
	}

}
