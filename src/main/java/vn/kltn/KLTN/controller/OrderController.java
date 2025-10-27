package vn.kltn.KLTN.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.CartItem;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.OrderItem;
import vn.kltn.KLTN.entity.Point;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.enums.OrderStatus;
import vn.kltn.KLTN.model.OrderDetailDTO;
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
	public String confirmOrder(@ModelAttribute("userDTO") UserDTO userDTO, HttpServletRequest request) {
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
		order.setCart(null); // Xóa ràng buộc giữa order và cart
		cart.setOrder(null);
		orderService.add(order);

		Order newOrder = new Order(cart.getId() + "-" + System.currentTimeMillis());
		cart.setOrder(newOrder);
		cart.setCartItems(new ArrayList<CartItem>()); // Xóa tất cả sản phẩm tồn tại trong giỏ hàng
		cart.setTotalPrice(0);
		newOrder.setCart(cart);
		orderService.add(newOrder); // Cập nhật order mới cho cart
		return "redirect:/user/profile";
	}

	@GetMapping("/show-detail/{id}")
	public String showDetail(@PathVariable("id") String orderId, Model model) {
		OrderDetailDTO orderDetailDTO = orderService.findById(orderId);
		Order order = orderDetailDTO.getOrder();
		List<OrderItem> orderItems = orderDetailDTO.getOrderItems();
		model.addAttribute("orderItems", orderItems);
		model.addAttribute("order", order);
		return "order-detail";
	}

	@GetMapping("/remove/{id}")
	public String removeOrder(@PathVariable("id") String orderId) {
		try {
			orderService.remove(orderId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/user/profile";
	}
}
