package service;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.kltn.KLTN.KltnApplication;
import vn.kltn.KLTN.service.FileService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class FileServiceTest {
	@Autowired
	private FileService fileService;

	@Test
	public void saveImage() {
		String path = System.getProperty("user.dir") + File.separator + "images" + File.separator + "social-share.jpg";
		fileService.uploadImageFileToCloudFly(path);
	}
}
