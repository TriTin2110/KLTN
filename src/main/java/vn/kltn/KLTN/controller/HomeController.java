package vn.kltn.KLTN.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	private ProductService productService;

	@GetMapping("")
	public String showHomePage(Model model) {
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "index";
	}
}
