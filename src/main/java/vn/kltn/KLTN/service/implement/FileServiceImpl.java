package vn.kltn.KLTN.service.implement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import vn.kltn.KLTN.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	private AmazonS3 amazonS3;
	@Value("${cloudfly.bucketName}")
	private String bucketName;

	@Autowired
	public FileServiceImpl(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	@Override
	public String uploadImageFileToCloudFly(MultipartFile multipartFile) {
		// TODO Auto-generated method stub
		File file = new File(multipartFile.getOriginalFilename());
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(multipartFile.getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String keyName = "images/" + file.getName();
		PutObjectRequest request = new PutObjectRequest(bucketName, keyName, file);

		try {
			amazonS3.putObject(request);
		} catch (NullPointerException e) {
			// TODO: handle exception
			file.delete();
			return "https://s3.cloudfly.vn/kltn/" + keyName;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			file.delete();
		}
		return null;
	}
}
