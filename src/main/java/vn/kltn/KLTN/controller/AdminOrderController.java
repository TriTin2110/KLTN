package vn.kltn.KLTN.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.enums.OrderStatus;
import vn.kltn.KLTN.service.OrderService;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {
	private OrderService orderService;

	@Autowired
	public AdminOrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/")
	public String showOrderList(Model model) {
		List<Order> order = orderService.findAllWithItems();
		model.addAttribute("orders", order);
		try {
			// cần lưu dưới dạng Json vì order
			// có cấu trúc phức tạp không
			// thể khới tạo trực tiếp từ
			// javascript bên thymeleaf
			model.addAttribute("orderJson", new ObjectMapper().writeValueAsString(order));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "admin/order/list";
	}

	@PostMapping("/update/status")
	@ResponseBody
	public Map<String, Object> updateStatus(@RequestBody Map<String, String> data) {
		int rowAffected = orderService.updateStatus(data.get("orderId"), data.get("status"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", (rowAffected > 0) ? true : false);
		map.put("status", OrderStatus.valueOf(data.get("status")));
		map.put("orderId", data.get("orderId"));
		return map;
	}
}
