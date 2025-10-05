package vn.kltn.KLTN.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.Point;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.enums.RoleAvailable;
import vn.kltn.KLTN.service.RoleService;
import vn.kltn.KLTN.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService service;

	@GetMapping("/sign-up")
	public String showSignUpPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "sign-up";
	}

	@GetMapping("/sign-in")
	public String showSignInPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "sign-in";
	}

	@PostMapping("/sign-up")
	public String signUp(@ModelAttribute("user") User user) {
		Cart cart = new Cart(user.getUsername());
		Order order = new Order(user.getUsername() + "-" + System.currentTimeMillis());
		Point point = new Point(user.getUsername());

		user.addCart(cart);
		cart.addOrder(order);
		point.addOrder(order);
		point.addUser(user);
		user.setRole(roleService.findByType(RoleAvailable.ROLE_USER));

		user = service.signUp(user);
		if (user != null)
			return "notice/sign-up-success";
		return null;
	}

}
