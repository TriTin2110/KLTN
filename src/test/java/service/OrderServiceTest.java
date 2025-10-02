package service;

import java.sql.Date;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.kltn.KLTN.KltnApplication;
import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.enums.OrderStatus;
import vn.kltn.KLTN.service.CartService;
import vn.kltn.KLTN.service.OrderService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class OrderServiceTest {
	/*-
	 * Điều kiện 
	 * 	1. Product phải nằm trong giỏ hàng
	 * Yêu cầu
	 * 	1. Khi order đã được cập nhật giá trị mới thì giỏ hàng sẽ trống
	 * 	2. id của order sẽ có dạng là: cart_id + random_number 
	 * */
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;

	@Test
	public void order() {
		Cart cart = cartService.findById("tinnguyen");
		Order order = cart.getOrder();
		Map<Product, Integer> orderedItems = cart.getCartItems();
		order.setOrderItem(orderedItems);
		order.setTotalPrice(orderedItems.keySet().stream().mapToInt(o -> o.getPrice() * orderedItems.get(o)).sum());
		order.setStatus(OrderStatus.PENDING);
		order.setCreatedDate(new Date(System.currentTimeMillis()));
		cart.returnToOriginState();
		orderService.add(order);
	}
}
