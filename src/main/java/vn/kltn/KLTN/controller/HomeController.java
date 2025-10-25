package vn.kltn.KLTN.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		List<Product> products = productService.findAll();
		Optional<Product> opt = products.stream().filter(product -> id.equals(product.getName())).findFirst();
		Product product = (opt.isEmpty()) ? null : opt.get();
		if (product != null) {
			List<Product> sameProducts = products.stream()
					.filter(p -> product.getCategory().getName().equals(p.getCategory().getName()))
					.collect(Collectors.toList());
			sameProducts.removeIf(p -> p.getName().equals(product.getName())); // Xóa sản phẩm bị trùng với sản phẩm
																				// hiện
			model.addAttribute("sameProducts", sameProducts);
		}
		model.addAttribute("product", product);

		return "detail-product";
	}
}
