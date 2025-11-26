package vn.kltn.KLTN.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.kltn.KLTN.entity.Category;
import vn.kltn.KLTN.entity.Event;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.enums.EventStatus;
import vn.kltn.KLTN.model.CategoryItemDTO;
import vn.kltn.KLTN.service.CategoryService;
import vn.kltn.KLTN.service.EventService;
import vn.kltn.KLTN.service.FileService;

@Controller
@RequestMapping("/admin/event")
public class AdminEvent {
	@Autowired
	private EventService eventService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private FileService fileService;

	@GetMapping("")
	public String showEventPage(Model model) {
		List<Event> events = eventService.findAll();
		List<Category> categories = categoryService.findAll();
		Event event = new Event();
		model.addAttribute("event", event);
		model.addAttribute("events", events);
		model.addAttribute("categories", categories);
		return "admin/event/list";
	}

	@PostMapping("/insert")
	public void insertEvent(@ModelAttribute("event") Event event, @RequestParam("image-chooser") MultipartFile file) {
		if (eventService.findById(event.getName()) == null) // Event chưa tồn tại
		{
			event.setEventStatus(EventStatus.ON_QUEUE); // Mặc định khi tạo event ra thì giá trị = ON_QUEUE, sau đó cứ
														// mỗi phút thì event sẽ được kiểm tra trạng thái 1 lần
			String mainImageFileName = fileService.uploadImageFileToCloudFly(file, "images/event/main-image/",
					file.getOriginalFilename());
			event.setImage(mainImageFileName);
			List<Category> categories = categoryService.findAll();
			List<Product> products = null;
			for (CategoryItemDTO dto : event.getItems()) {
				for (Category category : categories) {// Duyệt từng categories trong DB
					if (category.getName().equals(dto.getCategoryName())) {// Nếu có category nào nằm trong danh sách
																			// event thì bóc từng product ra và set
																			// discount
						products = category.getProducts();
						for (Product product : products) {
							product.setDiscount(dto.getDiscount());
							product.setEventStatus(EventStatus.ON_QUEUE);
							product.setEvent(event);
							event.addProduct(product);
						}
					}
				}
			}
			eventService.add(event);
		}

	}
}
