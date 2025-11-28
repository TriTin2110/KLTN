package vn.kltn.KLTN.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.kltn.KLTN.entity.Coupon;
import vn.kltn.KLTN.entity.Event;
import vn.kltn.KLTN.enums.EventStatus;
import vn.kltn.KLTN.service.CouponService;
import vn.kltn.KLTN.service.EventService;

@Controller
@RequestMapping("/admin/coupon")
public class AdminCouponController {
	@Autowired
	private CouponService couponService;
	@Autowired
	private EventService eventService;

	@GetMapping("")
	public String showCouponDashboard(Model model) {
		List<Coupon> coupons = couponService.findAll();
		List<Event> events = eventService.findAll();
		Coupon coupon = new Coupon();
		model.addAttribute("coupon", coupon);
		model.addAttribute("coupons", coupons);
		model.addAttribute("events", events);
		return "admin/coupon/list";
	}

	@PostMapping("/insert")
	public String insert(@ModelAttribute("coupon") Coupon coupon, @RequestParam("event") String eventId) {
		Coupon couponDB = couponService.findById(coupon.getId());
		if (couponDB == null) {
			if (!"default".equalsIgnoreCase(eventId))// Khi mã giảm giá có event
			{
				Event event = eventService.findById(eventId);
				coupon.setEvent(event);
			}
			coupon.setEventStatus(EventStatus.ON_QUEUE);
			System.out.println(coupon);
			couponService.add(coupon);
		}
		return "redirect:/admin/coupon";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		couponService.remove(id);
		return "redirect:/admin/coupon";
	}
}
