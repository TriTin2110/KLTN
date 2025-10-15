package vn.kltn.KLTN.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.service.CartService;
import vn.kltn.KLTN.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;

	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> addCart(HttpServletRequest request, @RequestBody Map<String, String> data, Model model) {
		User user = (User) request.getSession().getAttribute("user");
		Map<String, Object> result = new HashMap<String, Object>();
		if (user != null) {
			String productName = data.get("productName");
			int productQuantity = Integer.parseInt(data.get("productQuantity"));
			int price = Integer.parseInt(data.get("price"));
			String size = data.get("size");

			Cart cart = cartService.addProductToCart(user.getUsername(), productName, size, price, productQuantity);
			cart = cartService.update(cart);
			if (cart == null)
				result.put("success", false);
			else {
				result.put("success", true);
				model.addAttribute("cart", cart);
				user.setCart(cart);
			}
			return result;
		}
		result.put("success", false);
		return result;
	}
}
