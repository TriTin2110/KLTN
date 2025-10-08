package vn.kltn.KLTN.service.implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import vn.kltn.KLTN.entity.Ingredient;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.service.FileService;
import vn.kltn.KLTN.service.ProductService;

@Service
public class FileServiceImpl implements FileService {
	private AmazonS3 amazonS3;
	@Value("${cloudfly.bucketName}")
	private String bucketName;
	@Autowired
	private ProductService productService;

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

	@Override
	public List<Product> readXLSXFile(String path) {
		// TODO Auto-generated method stub
		Map<String, Product> map = new LinkedHashMap<String, Product>();
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			boolean isHeader = true;
			Product product = null;
			for (Row row : sheet) {
				if (isHeader) {
					isHeader = false;
					continue;
				}
				String name = getValue(sheet, row.getRowNum(), 0);
				String image = getValue(sheet, row.getRowNum(), 1);
				String size = getCellValue(row.getCell(2));
				String price = getCellValue(row.getCell(3));
				String ingredients = getCellValue(row.getCell(4));
				String categoryId = getValue(sheet, row.getRowNum(), 5);
				String discount = getValue(sheet, row.getRowNum(), 6);
				if (name != null && !map.containsKey(name)) {
					product = new Product(name, image);
					productService.insertAddtionInformation(product, size, price, ingredients, categoryId, discount);
				}
				if (name != null && size != null && !size.isBlank() && !product.getSizes().contains(size)) {
					product.getSizes().add(size);
				}
				if (name != null && price != null && !price.isBlank()) {
					price = price.replaceAll("[^0-9-]", "");
					int p = (price.isBlank()) ? 0 : Integer.parseInt(price);
					if (!product.getPrices().contains(p)) {
						product.getPrices().add(p);
					}
				}
				if (name != null && ingredients != null && !ingredients.isBlank()) {
					Ingredient i = new Ingredient(ingredients, 0, 0);
					product.addIngredient(i);
				}
				map.put(name, product);
			}
			System.out.println(sheet.getLastRowNum());
			workbook.cloneSheet(0);
			workbook.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ArrayList<Product>(map.values());
	}

	private String getValue(Sheet sheet, int row, int col) {
		/*-
		 * Duyệt qua tất cả các ô merge trong sheet 
		 * kiểm tra địa chỉ (hàng, cột) của ô merge đó có cùng với row, col không
		 */
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress region = sheet.getMergedRegion(i);
			if (region.isInRange(row, col)) {
				Row firstRow = sheet.getRow(region.getFirstRow());
				Cell cell = firstRow.getCell(region.getFirstColumn());
				return getCellValue(cell);
			}
		}
		return null;
	}

	private String getCellValue(Cell cell) {
		if (cell == null)
			return "";
		if (cell.getCellType() == CellType.NUMERIC)
			return String.valueOf(cell.getNumericCellValue()).trim();
		if (cell.getCellType() == CellType.STRING)
			return cell.getStringCellValue().trim();

		return "";
	}
}
