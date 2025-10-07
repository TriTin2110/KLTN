package vn.kltn.KLTN.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public String uploadImageFileToCloudFly(MultipartFile multipartFile);
}
