package vn.kltn.KLTN.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.kltn.KLTN.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {
	private FileService fileService;

	@Autowired
	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
		try {
			String url = fileService.uploadImageFileToCloudFly(multipartFile);
			return ResponseEntity.ok(url);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
}
