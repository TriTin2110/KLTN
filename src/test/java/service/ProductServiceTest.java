package service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.kltn.KLTN.KltnApplication;
import vn.kltn.KLTN.entity.Category;
import vn.kltn.KLTN.entity.Ingredient;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.entity.ProductDetail;
import vn.kltn.KLTN.entity.Supplier;
import vn.kltn.KLTN.service.CategoryService;
import vn.kltn.KLTN.service.FileService;
import vn.kltn.KLTN.service.IngredientService;
import vn.kltn.KLTN.service.ProductService;
import vn.kltn.KLTN.service.SupplierService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ProductServiceTest {
	@Autowired
	private ProductService service;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private IngredientService ingredientService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private FileService fileService;

	@Test
	@Disabled
	public void add() {
		Category category = new Category("Cafe", null);
		Supplier supplier = supplierService.findById("The Coffe House");
		if (supplier == null) {
			supplier = new Supplier("The Coffe House", "Quận 1", 1231231232);
		}
		Ingredient ingredient = new Ingredient("Đá", 100, 10000);
		Product product = new Product("Cafe đá", null);
		product.getPrices().add(10000);
		ProductDetail productDetai = new ProductDetail(product.getName(), "Cafe đá", "Còn hàng", product);
		product.setProductDetail(productDetai);
		product.addIngredient(ingredient);
		supplier.addIngredient(ingredient);
		category.addProduct(product);
		assertTrue(service.add(product));
	}

	@Test
	@Disabled
	public void delete() {
		String productId = "Trà sữa truyền thống";
		assertTrue(service.remove(productId));
	}

	@Test
	@Disabled
	public void update() {
		String productId = "Trà sữa truyền thống";
		Product product = service.findById(productId);
		product.getPrices().add(10000);
		assertTrue(service.update(product));
	}

	@Test
	@Disabled
	public void findById() {
		String productId = "Trà sữa truyền thống";
		Product product = service.findById(productId);
		assertNotNull(product);
	}

	@Test
	@Disabled
	public void findAll() {
		List<Product> products = service.findAll();
		products.forEach(System.out::println);
	}

	@Test
	public void addListProduct() {
		String path = System.getProperty("user.dir") + File.separator + "KLTN_Dữ_liệu.xlsx";
		List<Product> products = fileService.readXLSXFile(path);
		for (Product product : products) {
			System.out.println(product);
		}
	}
}
