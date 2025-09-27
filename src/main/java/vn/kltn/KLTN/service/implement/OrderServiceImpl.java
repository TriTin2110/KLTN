package vn.kltn.KLTN.service.implement;

import java.util.List;

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
	public List<Order> checkingAll(String userName) {
		// TODO Auto-generated method stub
		return null;
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
			repository.save(order);
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
		return null;
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
