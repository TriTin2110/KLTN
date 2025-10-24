package vn.kltn.KLTN.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.OrderItem;
import vn.kltn.KLTN.entity.Point;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.enums.OrderStatus;
import vn.kltn.KLTN.model.UserDTO;
import vn.kltn.KLTN.service.CartService;
import vn.kltn.KLTN.service.OrderService;
import vn.kltn.KLTN.service.PointService;

@Controller
@RequestMapping("/order")
public class OrderController {
	private OrderService orderService;
	private CartService cartService;
	private PointService pointService;

	@Autowired
	public OrderController(OrderService orderService, CartService cartService, PointService pointService) {
		this.orderService = orderService;
		this.cartService = cartService;
		this.pointService = pointService;
	}

	@GetMapping("/show-order-input")
	public String showInputOrderPage(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Cart cart = user.getCart();
		UserDTO userDTO = new UserDTO(user.getUsername(), user.getEmail(), user.getAddress(), user.getFullName(),
				user.getPhoneNumber(), cart);
		model.addAttribute("userDTO", userDTO);
		model.addAttribute("cartItems", cart.getCartItems());
		model.addAttribute("totalPrice", cart.getTotalPrice());

		return "order-input";
	}

	@PostMapping("/show-confirm-order")
	public void confirmOrder(@ModelAttribute("userDTO") UserDTO userDTO, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Cart cart = user.getCart();
		Point point = user.getPoint();
		Order order = cart.getOrder();
		List<OrderItem> orderItems = cart.getCartItems().stream().map(o -> o.convertOrderItem()).toList();
		point = pointService.addOrder(point.getId(), order);

		order.setPhoneNumber(userDTO.getPhoneNumber());
		order.setAddress(userDTO.getAddress());
		order.setCreatedDate(new Date(System.currentTimeMillis()));
		order.setStatus(OrderStatus.PENDING);
		order.setTotalPrice(cart.getTotalPrice());
		order.setPoint(point);
		order.setOrderItem(orderItems);
		cart.setOrder(new Order(cart.getId() + "-" + System.currentTimeMillis()));
		orderService.add(order);

	}
}
