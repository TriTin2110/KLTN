package vn.kltn.KLTN.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/coupon")
public class AdminCouponController {
	@GetMapping("/")
	public String showCouponDashboard() {
		return "admin/coupon/dashboard";
	}
}
