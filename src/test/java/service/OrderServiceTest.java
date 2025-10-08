package service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.kltn.KLTN.KltnApplication;
import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.enums.OrderStatus;
import vn.kltn.KLTN.service.CartService;
import vn.kltn.KLTN.service.OrderService;
import vn.kltn.KLTN.service.UserService;

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
	@Autowired
	private UserService userService;

	@Test
//	@Disabled
	public void order() {
		Cart cart = cartService.findById("tinnguyen");
		Order order = new Order(cart.getId());
		Map<Product, Integer> orderedItems = cart.getCartItems();
		if (!orderedItems.isEmpty()) {
			order.setOrderItem(orderedItems);
			order.setTotalPrice(
					orderedItems.keySet().stream().mapToInt(o -> o.getPrices().get(0) * orderedItems.get(o)).sum());
			order.setStatus(OrderStatus.PENDING);
			order.setCreatedDate(new Date(System.currentTimeMillis()));
			cart.returnToOriginState();
			orderService.add(order);
		}
	}

	@Test
	@Disabled
	public void showOrder() {
		User user = userService.findById("tinnguyen");
		List<Order> orders = orderService.checkingAll(user.getUsername());
		orders.forEach(System.out::println);
	}

	@Test
	@Disabled
	public void remove() {
		orderService.remove("tinnguyen1759404280007");
	}
}
