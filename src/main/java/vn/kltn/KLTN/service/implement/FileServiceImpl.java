package vn.kltn.KLTN.service.implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import vn.kltn.KLTN.service.FileService;
import vn.kltn.KLTN.service.IngredientService;
import vn.kltn.KLTN.service.ProductService;
import vn.kltn.KLTN.service.SupplierService;

@Service
public class FileServiceImpl implements FileService {
	private AmazonS3 amazonS3;
	@Value("${cloudfly.bucketName}")
	private String bucketName;
	@Autowired
	private ProductService productService;
	@Autowired
	private IngredientService ingredientService;
	@Autowired
	private SupplierService supplierService;

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
		PutObjectRequest request = new PutObjectRequest(bucketName, keyName, file); // Tạo request để gửi file lên

		return uploadFileToCloud(request, file, keyName);
	}

	public String uploadImageFileToCloudFly(String path) {
		// TODO Auto-generated method stub
		File file = new File(path);
		String keyName = "images/" + file.getName();
		PutObjectRequest request = new PutObjectRequest(bucketName, keyName, file); // Tạo request để gửi file lên
		return uploadFileToCloud(request, file, keyName);
	}

	private String uploadFileToCloud(PutObjectRequest request, File file, String keyName) {
		try {
			amazonS3.putObject(request); // Tiến hành gửi file
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

	@Override
	public void readXLSXFile(String path) {
		// TODO Auto-generated method stub
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook workbook = new XSSFWorkbook(fis);
			productService.addMultipleProductFromFile(workbook, ingredientService, supplierService);
			workbook.cloneSheet(0);
			workbook.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		return new ArrayList<Product>(map.values());
	}

}
