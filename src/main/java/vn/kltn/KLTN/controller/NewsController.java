package vn.kltn.KLTN.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import vn.kltn.KLTN.entity.News;
import vn.kltn.KLTN.service.FileService;
import vn.kltn.KLTN.service.NewsService;

@Controller
@RequestMapping("/news")
public class NewsController {
	@Autowired
	private NewsService newsService;
	@Autowired
	private FileService fileService;

	@GetMapping("/editor")
	public String showEditor(Model model) {
		News news = new News();
		List<News> newsList = newsService.findAll();
		model.addAttribute("news", news);
		model.addAttribute("newsList", newsList);
		return "/admin/news/list";
	}

	@PostMapping("/insert")
	public String insertNews(@ModelAttribute("news") News news, @RequestParam("image-chooser") MultipartFile file,
			@RequestParam("content") String content, Model model) {
		List<News> newsList = newsService.findAll();
		boolean newAlreadyExists = newsList.stream().anyMatch(n -> news.getName().equals(n.getName()));
		if (!newAlreadyExists) {
			try {
				String mainImageFileName = fileService.uploadImageFileToCloudFly(file, "images/news/main-image/",
						file.getOriginalFilename());
				news.setImage(mainImageFileName);
				news.setAuthorName("ADMIN");
				news.setContent(content);
				newsService.add(news);
				newsService.updateCache();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				model.addAttribute("error", "Đã có lỗi xảy ra: " + e.getMessage());
				return "redirect:/news/editor";
			}
		}
		model.addAttribute("success", "Thêm tin tức mới thành công!");
		return "redirect:/news/editor";
	}

	@PostMapping("/upload-image")
	@ResponseBody
	public Map<String, Object> uploadImageToCloud(@RequestParam("upload") MultipartFile file) { // CKEditor 5 (CKFinder)
																								// gửi file ảnh lên
																								// server
		// bằng trường có tên upload
		String imageName = fileService.uploadImageFileToCloudFly(file, "images/news/", file.getOriginalFilename());
		return Map.of("uploaded", true, "url", "https://s3.cloudfly.vn/kltn/images/news/" + imageName);// trả về theo
																										// chuẩn của
																										// chuẩn
																										// CKEditor 5
	}

	@GetMapping("/delete/{id}")
	public String deleteNews(@PathVariable("id") String id, Model model) {
		try {
			newsService.remove(id);
			newsService.updateCache();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("error", "Đã có lỗi xảy ra: " + e.getMessage());
			return "redirect:/news/editor";
		}
		model.addAttribute("success", "Xóa thành công!");
		return "redirect:/news/editor";
	}

	@PostMapping("/update")
	public String updateNews(@ModelAttribute("news") News news, Model model,
			@RequestParam("image-chooser") MultipartFile file) {
		try {
			if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank()) {
				String mainImageFileName = fileService.uploadImageFileToCloudFly(file, "images/news/main-image/",
						file.getOriginalFilename());
				news.setImage(mainImageFileName);
				System.out.println("Đã upload ảnh");
			}
			newsService.update(news);
			newsService.updateCache();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("error", "Đã có lỗi xảy ra: " + e.getMessage());
			return "redirect:/news/editor";
		}
		model.addAttribute("success", "Xóa thành công!");
		return "redirect:/news/editor";
	}

	
		//  Hiển thị danh sách bài viết công khai
		@GetMapping("")
		public String listPublicNews(Model model) {
		    List<News> newsList = newsService.findAll();
		    model.addAttribute("newsList", newsList);
		    return "index";
			}

//		@GetMapping("/{id}")
//		public String detail(@PathVariable String id, Model model) {
//		    News news = newsService.findById(id);
//		    if (news == null) {
//		        throw new org.springframework.web.server.ResponseStatusException(
//		            org.springframework.http.HttpStatus.NOT_FOUND, "News not found");
//		    }
//		    model.addAttribute("news", news);
//		    return "detail-news";
//		}

	@GetMapping("/")
	public String showNewsListPage(Model model) {
		List<News> newsList = newsService.findAll();
		model.addAttribute("newsList", newsList);
		return "news";
	}

	@GetMapping("/{id}")
	public String showNewsPage(@PathVariable("id") String id, Model model) {
		List<News> newsList = newsService.findAll();
		Optional<News> opt = newsList.stream().filter(n -> n.getName().equals(id)).findFirst();
		if (opt.isEmpty())
			return "redirect:/news/";
		News news = opt.get();
		model.addAttribute("news", news);
		return "detail-news";
	}
}

