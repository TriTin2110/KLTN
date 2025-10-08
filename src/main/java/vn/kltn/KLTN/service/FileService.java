package vn.kltn.KLTN.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import vn.kltn.KLTN.entity.Product;

public interface FileService {
	public String uploadImageFileToCloudFly(MultipartFile multipartFile);

	public List<Product> readXLSXFile(String path);
}
