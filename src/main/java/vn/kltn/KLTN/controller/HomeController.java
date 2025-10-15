package vn.kltn.KLTN.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	private ProductService productService;

	@GetMapping("")
	public String showHomePage(Model model, HttpServletRequest request) {
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "index";
	}

	@GetMapping("/san-pham/{id}")
	public String showHomePage(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		Product product = productService.findById(id.trim());
		List<Product> sameProducts = productService.findByCategory(product.getCategory().getName());
		sameProducts.removeIf(p -> p.getName().equals(product.getName())); // Xóa sản phẩm bị trùng với sản phẩm hiện
		model.addAttribute("product", product);
		model.addAttribute("sameProducts", sameProducts);
		return "detail-product";
	}

}
