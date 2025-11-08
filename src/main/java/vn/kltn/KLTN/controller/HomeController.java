package vn.kltn.KLTN.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.entity.News;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.modules.ProductScore;
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
		List<ProductScore> productScores = new ArrayList<ProductScore>();
		content = StringHandler.handlingString(content);
		LevenshteinDistance ld = new LevenshteinDistance();

		String productName = null;
		String productCategory = null;
		double nameScore;
		double categoryScore;
		double maxScore;
		for (Product product : products) {
			productName = StringHandler.handlingString(product.getName());
			productCategory = StringHandler.handlingString(product.getCategory().getName());

			// Nếu từ khóa nằm trong tên của sản phẩm thì thêm sản phẩm vào kết quả
			if (productName.contains(content)) {
				productScores.add(new ProductScore(1, product));
				continue;
			}
			nameScore = similarity(content, productName, ld);
			categoryScore = similarity(content, productCategory, ld);
			maxScore = Math.max(nameScore, categoryScore);

			if (maxScore > 0.4) // Lấy kết quả có độ tương đồng > 0.5
			{
				productScores.add(new ProductScore(maxScore, product));
			}
		}
		List<Product> result = productScores.stream().sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
				.map(p -> p.getProduct()).toList();

		model.addAttribute("products", result);
		return "search-result";
	}

	// Tìm kiếm thông qua độ tương đồng
	// Levenshtein distance là số lần chỉnh sửa tối thiểu (thêm, xóa, thay ký tự)
	// cần để biến chuỗi s1 thành s2.
	private double similarity(String s1, String s2, LevenshteinDistance ld) {
		if (s1 == null || s2 == null || s1.isBlank() || s1.isBlank())
			return 0;
		int distance = ld.apply(s1, s2); // -So sánh khoảng cách giữ 2 chuỗi (giá trị càng cao thì khoảng cách càng lớn)
		int maxLength = Math.max(s1.length(), s2.length());// Dùng để chuẩn hóa kết quả
		return 1 - (double) distance / maxLength; // Giá trị từ 0 -> 1 (1 giống hoàn toàn)
	}

}
