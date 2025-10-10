package vn.kltn.KLTN.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public String uploadImageFileToCloudFly(MultipartFile multipartFile);

	public String uploadImageFileToCloudFly(String path);

	public void readXLSXFile(String path);
}
