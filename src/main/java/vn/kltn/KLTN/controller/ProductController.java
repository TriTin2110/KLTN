package vn.kltn.KLTN.controller;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.repository.ProductRepository;
import vn.kltn.KLTN.service.CommentService;
import vn.kltn.KLTN.service.FileService;
import vn.kltn.KLTN.service.ProductService;

@Controller
@RequestMapping("/product/admin")
public class ProductController {

	@Autowired
	private FileService fileService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CommentService commentService;

	@Value("${upload.dir:src/main/resources/static/images/}")
	private String uploadDir;

	@GetMapping("/show-page")
	public String showAdminPage(Model model) {
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("product", new Product());
		return "admin";
	}

	@GetMapping("/edit")
	public String editForm(@RequestParam("name") String name, Model model, RedirectAttributes redirectAttributes) {
		String trimmedName = name.trim();
		System.out.println("DEBUG: Searching for name='" + trimmedName + "'"); // Log tạm để test
		Optional<Product> optional = productRepository.findById(trimmedName);
		if (optional.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại!");
			return "redirect:/product/admin/show-page";
		}
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("editProduct", optional.get());
		return "admin";
	}

//	fileService.uploadImageFileToCloudFly(imageFile)
	@PostMapping("/insert")
	public String insert(@RequestParam("name") String name, @RequestParam("prices") String prices,
			@RequestParam("sizes") String sizes, @RequestParam("imageFile") MultipartFile imageFile,
			RedirectAttributes redirectAttributes) {
		try {
			Product p = new Product();
			p.setName(name);

			// Upload ảnh lên cloud
			String imageUrl;
			if (imageFile != null && !imageFile.isEmpty()) {
				imageUrl = fileService.uploadImageFileToCloudFly(imageFile);
			} else {
				imageUrl = "/images/no-image.png"; // Ảnh mặc định nếu không upload
			}
			p.setImage(imageUrl);

			// Chuyển chuỗi giá thành danh sách số
			List<Integer> priceList = Arrays.stream(prices.split(",")).filter(s -> !s.trim().isEmpty()).map(s -> {
				try {
					return Integer.parseInt(s.trim());
				} catch (NumberFormatException e) {
					return 0;
				}
			}).collect(Collectors.toList());

			// Chuyển chuỗi size thành danh sách chuỗi
			List<String> sizeList = Arrays.stream(sizes.split(",")).map(String::trim).filter(s -> !s.isEmpty())
					.collect(Collectors.toList());

			// Thêm danh sách price, size vào trong map
			Map<String, Integer> sizePrice = p.getSizePrice();
			for (int i = 0; i < sizeList.size(); i++) {
				sizePrice.put(sizeList.get(i), priceList.get(i));
			}
			p.setSizePrice(sizePrice);
//			p.setPrices(priceList);
//			p.setSizes(sizeList);

			// Lưu vào DB
			productRepository.save(p);
			redirectAttributes.addFlashAttribute("success", "Thêm sản phẩm thành công!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Lỗi thêm sản phẩm: " + e.getMessage());
		}

		return "redirect:/product/admin/show-page";
	}

	@PostMapping("/update")
	public String update(@RequestParam("name") String name,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
			@RequestParam("prices") String prices, @RequestParam("sizes") String sizes,
			RedirectAttributes redirectAttributes) {
		try {
			String trimmedName = name.trim();
			if (trimmedName.isEmpty()) {
				redirectAttributes.addFlashAttribute("error", "Tên sản phẩm không được rỗng!");
				return "redirect:/product/admin/show-page";
			}

			// Tìm sản phẩm cũ trong DB
			Product existing = productService.findById(trimmedName);
			if (existing == null) {
				redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm cần cập nhật!");
				return "redirect:/product/admin/show-page";
			}

			// Cập nhật tên
			existing.setName(trimmedName);

			// Nếu có ảnh mới thì upload lên cloud
			if (imageFile != null && !imageFile.isEmpty()) {
				String imageUrl = fileService.uploadImageFileToCloudFly(imageFile);
				existing.setImage(imageUrl);
			}
			// Nếu không upload ảnh mới, giữ nguyên ảnh cũ
			else if (existing.getImage() == null || existing.getImage().isEmpty()) {
				existing.setImage("/images/no-image.png");
			}

			// Cập nhật danh sách giá
			List<Integer> priceList = Arrays.stream(prices.split(",")).filter(s -> !s.trim().isEmpty()).map(s -> {
				try {
					return Integer.parseInt(s.trim());
				} catch (NumberFormatException ex) {
					return 0;
				}
			}).collect(Collectors.toList());

			// Cập nhật danh sách kích thước
			List<String> sizeList = Arrays.stream(sizes.split(",")).map(String::trim).filter(s -> !s.isEmpty())
					.collect(Collectors.toList());

			// Cập nhật kích thước và giá (theo map)
			Map<String, Integer> sizePrice = existing.getSizePrice();
			for (int i = 0; i < sizeList.size(); i++) {
				sizePrice.put(sizeList.get(i), priceList.get(i));
			}
			existing.setSizePrice(sizePrice);

//			existing.setPrices(priceList);
//			existing.setSizes(sizeList);

			// Lưu lại sản phẩm
			productRepository.save(existing);

			redirectAttributes.addFlashAttribute("success", "Cập nhật sản phẩm thành công!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Lỗi cập nhật sản phẩm: " + e.getMessage());
		}

		return "redirect:/product/admin/show-page";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam("name") String name, RedirectAttributes redirectAttributes) {
		if (!productRepository.existsById(name)) {
			redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại!");
		} else {
			productRepository.deleteById(name);
			redirectAttributes.addFlashAttribute("success", "Xoá sản phẩm thành công!");
		}
		return "redirect:/product/admin/show-page";
	}

	@PostMapping("/import-xlsx")
	public String importDataFormXLSXFile(@RequestParam("file") MultipartFile file, Model model) {
		fileService.readXLSXFile(file);
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("product", new Product());
		return "redirect:/product/admin/show-page";
	}
	
	// comment san pham
	@GetMapping("/san-pham/{id}")
	public String showProductDetail(@PathVariable("id") String idEncoded, Model model) {
	    String name = UriUtils.decode(idEncoded, StandardCharsets.UTF_8);

	    Product product = productService.findById(name);
	    if (product == null) return "redirect:/?error=product_not_found";

	    model.addAttribute("product", product);
	    model.addAttribute("comments", commentService.findAllByProductId(name));

	    return "detail-product";
	}




}
