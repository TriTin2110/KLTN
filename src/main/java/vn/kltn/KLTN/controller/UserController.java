package vn.kltn.KLTN.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.Point;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.enums.RoleAvailable;
import vn.kltn.KLTN.modules.PasswordEncode;
import vn.kltn.KLTN.service.PointService;
import vn.kltn.KLTN.service.RoleService;
import vn.kltn.KLTN.service.UserService;
import vn.kltn.KLTN.service.VerifyService;

@Controller
@RequestMapping("/user")
public class UserController {
	private RoleService roleService;
	private UserService service;
	private VerifyService verifyService;
	private PointService pointService;

	@Autowired
	public UserController(RoleService roleService, UserService service, VerifyService verifyService,
			PointService pointService) {
		this.roleService = roleService;
		this.service = service;
		this.verifyService = verifyService;
		this.pointService = pointService;
	}

	@GetMapping("/sign-up")
	public String showSignUpPage(Model model) {
		User user = new User();
		model.addAttribute("register", user);
		return "sign-up";
	}

	@GetMapping("/sign-in")
	public String showSignInPage() {
		return "sign-in";
	}

	@PostMapping("/sign-up")
	public String signUp(@ModelAttribute("register") User user, Model model) {
		if (service.findById(user.getUsername()) != null) {
			model.addAttribute("error", "User đã tồn tại!");
			return "sign-up";
		}
		Cart cart = new Cart(user.getUsername());
		Order order = new Order(cart.getId() + "-" + System.currentTimeMillis());
		Point point = new Point(user.getUsername());

		user.addCart(cart);
		cart.addOrder(order);
		user.setPoint(point);
		user.setRole(roleService.findByType(RoleAvailable.ROLE_USER));

		user = service.signUp(user);
		if (user != null)
			return "notice/sign-up-success";
		return null;
	}

	@GetMapping("/sign-in-success")
	public String signIn(@AuthenticationPrincipal User user, HttpServletRequest request) {
		request.getSession().setAttribute("user", user);
		return "redirect:/";
	}

	@PostMapping("/verify-code")
	@ResponseBody
	public Map<String, Object> sendVerifyCode(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		verifyService.sendMail(email);
		Map<String, Object> response = new HashMap<String, Object>();
		if (email != null) {
			response.put("success", true);
			response.put("text", "Mã xác thực đã được gửi");
		}
		return response;
	}

	@PostMapping("/reset-password")
	@ResponseBody
	public Map<String, Object> resetPassword(@RequestBody Map<String, String> data) {
		String email = data.get("email");
		String password = data.get("password");
		String verifyCode = data.get("verifyCode");
		Map<String, Object> response = new HashMap<String, Object>();
		if (!verifyService.checkVerifyCode(email, verifyCode)) {
			response.put("success", false);
			response.put("text", "Mã xác nhận không chính xác");
		} else {
			User user = service.findByEmail(email);
			if (user == null) {
				response.put("success", false);
				response.put("text", "Người dùng không tồn tại!");
			} else {
				try {
					user.setPassword(PasswordEncode.encodePassword(password));
					service.update(user);
					response.put("success", true);
					response.put("text", "Đã cập nhật thành công!");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	@GetMapping("/profile")
	public String userProfile(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Point point = user.getPoint();
		List<Order> orders = pointService.getAllOrder(point.getId());
		model.addAttribute("user", user);
		model.addAttribute("orders", orders);
		return "profile";
	}

	@PostMapping("/profile-update")
	@ResponseBody
	public Map<String, Object> profileUpdate(@RequestBody Map<String, String> data, HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		String fullName = data.get("fullName");
		String address = data.get("address");
		String phoneNumber = data.get("phoneNumber");
		if (!fullName.isBlank() || fullName != null) {
			user.setFullName(fullName);
		}
		if (!address.isBlank() || address != null) {
			user.setAddress(address);
		}
		if (!phoneNumber.isBlank() || phoneNumber != null) {
			user.setPhoneNumber(phoneNumber);
		}
		try {
			service.update(user);
			response.put("success", true);
			response.put("text", "Đã cập nhật thông tin thành công!");
			return response;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
