package vn.kltn.KLTN.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.repository.ProductRepository;
import vn.kltn.KLTN.service.ProductService;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product/admin")
public class ProductController {

	@Autowired
    private ProductService productService;
	
    @Autowired
    private ProductRepository productRepository;

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
        String trimmedName = name.trim();  // Thêm dòng này
        System.out.println("DEBUG: Searching for name='" + trimmedName + "'");  // Log tạm để test
        Optional<Product> optional = productRepository.findById(trimmedName);
        if (optional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại!");
            return "redirect:/product/admin/show-page";
        }
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("editProduct", optional.get());
        return "admin";
    }

    @PostMapping("/insert")
    public String insert(@RequestParam("name") String name,
                         @RequestParam("prices") String prices,
                         @RequestParam("sizes") String sizes,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         RedirectAttributes redirectAttributes) {
        try {
            Product p = new Product();
            p.setName(name);

            // Upload ảnh
            if (imageFile != null && !imageFile.isEmpty()) {
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                File file = new File(dir, fileName);
                imageFile.transferTo(file);
                p.setImage("/images/" + fileName);
            } else {
                p.setImage("/images/no-image.png");
            }

            List<Integer> priceList = Arrays.stream(prices.split(","))
                    .filter(s -> !s.trim().isEmpty())
                    .map(s -> {
                        try {
                            return Integer.parseInt(s.trim());
                        } catch (NumberFormatException e) {
                            return 0;
                        }
                    })
                    .collect(Collectors.toList());

            List<String> sizeList = Arrays.stream(sizes.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            p.setPrices(priceList);
            p.setSizes(sizeList);

            productRepository.save(p);
            redirectAttributes.addFlashAttribute("success", "Thêm sản phẩm thành công!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi upload ảnh: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi thêm sản phẩm: " + e.getMessage());
        }
        return "redirect:/product/admin/show-page";
    }

    @PostMapping("/update")
    public String update(@RequestParam("name") String name,
                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                         @RequestParam("prices") String prices,
                         @RequestParam("sizes") String sizes,
                         RedirectAttributes redirectAttributes) {
        try {
            String trimmedName = name.trim();
            if (trimmedName.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Tên sản phẩm không được rỗng!");
                return "redirect:/product/admin/show-page";
            }

            // Tạo Product với fields cần update
            Product p = new Product();
            p.setName(trimmedName);

            // Upload ảnh mới nếu có
            if (imageFile != null && !imageFile.isEmpty()) {
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                File file = new File(dir, fileName);
                imageFile.transferTo(file);
                p.setImage("/images/" + fileName);
            } else {
                
                Product existing = productService.findById(trimmedName);
                if (existing != null) {
                    p.setImage(existing.getImage());
                } else {
                    p.setImage("/images/no-image.png");
                }
            }

            List<Integer> priceList = Arrays.stream(prices.split(","))
                    .filter(s -> !s.trim().isEmpty())
                    .map(s -> {
                        try { return Integer.parseInt(s.trim()); }
                        catch(NumberFormatException ex) { return 0; }
                    })
                    .collect(Collectors.toList());

            List<String> sizeList = Arrays.stream(sizes.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            p.setPrices(priceList);
            p.setSizes(sizeList);

            if (productService.update(p)) {
                redirectAttributes.addFlashAttribute("success", "Cập nhật sản phẩm thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Lỗi cập nhật sản phẩm (không tồn tại)!");
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi upload ảnh: " + e.getMessage());
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
}
