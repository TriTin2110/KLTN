package vn.kltn.KLTN.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public String uploadImageFileToCloudFly(MultipartFile multipartFile);

	public String uploadImageFileToCloudFly(String image);

	public void readXLSXFile(String path);

	public void delete(String image);
}
