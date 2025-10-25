package vn.kltn.KLTN.controller;

import java.util.Base64;
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

import jakarta.servlet.http.HttpServletRequest;
import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.CartItem;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.model.CartItemDTO;
import vn.kltn.KLTN.service.CartService;
import vn.kltn.KLTN.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;

	@GetMapping("/header")
	@ResponseBody
	public Map<String, Object> getCartItem(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Cart cart = user.getCart();
		Map<String, Object> data = new HashMap<String, Object>();
		List<CartItem> cartItems = cart.getCartItems();
		data.put("totalPrice", cart.getTotalPrice());
		data.put("cartItem", cartItems);
		return data;
	}

	@GetMapping("/show")
	public String showCartPage(HttpServletRequest request, Model model) {
		User user = (User) request.getSession().getAttribute("user");
		Cart cart = user.getCart();
		List<CartItem> cartItems = cart.getCartItems();
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalPrice", cart.getTotalPrice());
		return "cart";
	}

	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> addCart(HttpServletRequest request, @RequestBody CartItemDTO cartItemDTO, Model model) {
		User user = (User) request.getSession().getAttribute("user");
		Map<String, Object> result = new HashMap<String, Object>();
		if (user != null) {
			String productName = cartItemDTO.getProductId();
			String productImage = cartItemDTO.getProductImage();
			int productQuantity = cartItemDTO.getQuantity();
			int price = cartItemDTO.getPrice();
			String size = cartItemDTO.getSize();
			Cart cart = cartService.addProductToCart(user.getUsername(), productName, productImage, size, price,
					productQuantity, user.getCart());
			cart = cartService.update(cart);
			if (cart == null)
				result.put("success", false);
			else {
				result.put("success", true);
				result.put("totalPrice", cart.getTotalPrice());
				result.put("cartItem", cart.getCartItems());
				user.setCart(cart);
			}
			return result;
		}
		result.put("success", false);
		return result;
	}

	@PostMapping("/remove")
	@ResponseBody
	public Map<String, Object> removeItemCart(HttpServletRequest request, @RequestBody Map<String, String> data,
			Model model) {
		User user = (User) request.getSession().getAttribute("user");
		Map<String, Object> result = new HashMap<String, Object>();
		if (user != null) {
			String productName = data.get("productName");
			String userName = user.getUsername();
			String size = data.get("size");
			StringBuilder builder = new StringBuilder();
			builder.append(productName);
			builder.append("-");
			builder.append(userName);
			builder.append("-");
			builder.append(size);
			Cart cart = user.getCart();
			List<CartItem> cartItems = cart.getCartItems();
			String cartItemId = Base64.getEncoder().encodeToString(builder.toString().getBytes());
			for (CartItem CartItem : cartItems) {
				if (cartItemId.equals(CartItem.getItemId())) {
					cart.getCartItems().remove(CartItem);
					break;
				}
			}
			cart = cartService.update(cart);
			if (cart == null)
				result.put("success", false);
			else {
				result.put("success", true);
				result.put("totalPrice", cart.getTotalPrice());
				result.put("cartItem", cart.getCartItems());
				user.setCart(cart);
			}
			return result;
		}
		result.put("success", false);
		return result;
	}

}
