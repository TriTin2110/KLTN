package vn.kltn.KLTN.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.entity.News;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.modules.StringHandler;
import vn.kltn.KLTN.service.CommentService;
import vn.kltn.KLTN.service.NewsService;
import vn.kltn.KLTN.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private NewsService newsService;
	
	@GetMapping("")
	public String showHomePage(Model model, HttpServletRequest request) {
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		
		
		List<News> newsList = newsService.findAll();
        model.addAttribute("newsList", newsList);
		return "index";
	}

	@GetMapping("/san-pham/{id}")
	public String showHomePage(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		List<Product> products = productService.findAll();
		Optional<Product> opt = products.stream().filter(product -> id.equals(product.getName())).findFirst();
		List<Comment> comments = commentService.findAllByProductId(id);
		Product product = (opt.isEmpty()) ? null : opt.get();
		if (product != null) {
			List<Product> sameProducts = products.stream()
					.filter(p -> product.getCategory().getName().equals(p.getCategory().getName()))
					.collect(Collectors.toList());
			sameProducts.removeIf(p -> p.getName().equals(product.getName())); // Xóa sản phẩm bị trùng với sản phẩm
																				// hiện
			model.addAttribute("sameProducts", sameProducts);
		}
		product.sortSizePrice();
		model.addAttribute("product", product);
		model.addAttribute("comments", comments); // Danh sách comment của sản phẩm
		return "detail-product";
	}

	@GetMapping("/search/{content}")
	public String searchProduct(@PathVariable String content, Model model) {
		List<Product> products = productService.findAll();
		List<Product> result = new ArrayList<Product>();
		content = StringHandler.handlingString(content);
		String productName = null;
		String productCategory = null;
		for (Product product : products) {
			productName = StringHandler.handlingString(product.getName());
			productCategory = StringHandler.handlingString(product.getCategory().getName());
			if (productName.contains(content) || content.contains(productName) || productCategory.contains(content))
				result.add(product);
		}
		model.addAttribute("products", result);
		return "search-result";
	}
	

}
