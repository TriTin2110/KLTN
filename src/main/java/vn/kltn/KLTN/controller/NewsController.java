package vn.kltn.KLTN.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	public String showEditor() {
		return "/admin/news/list";
	}

	@PostMapping("/insert")
	public void insertNews(@RequestParam("content") String content) {
		System.out.println(content);
	}

	@PostMapping("/upload-image")
	@ResponseBody
	public Map<String, Object> uploadImageToCloud(@RequestParam("upload") MultipartFile file) { // CKEditor 5 (CKFinder)
																								// gửi file ảnh lên
																								// server
		// bằng trường có tên upload
		String imageName = fileService.uploadImageFileToCloudFly(file, "images/news/",
				UUID.randomUUID() + "_" + file.getOriginalFilename());
		return Map.of("uploaded", true, "url", "https://s3.cloudfly.vn/kltn/images/news/" + imageName);// trả về theo
																										// chuẩn của
																										// chuẩn
																										// CKEditor 5
	}
}
