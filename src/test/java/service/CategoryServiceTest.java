package service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.kltn.KLTN.KltnApplication;
import vn.kltn.KLTN.entity.Category;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.service.CategoryService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CategoryServiceTest {
	@Autowired
	private CategoryService service;

	@Test
	@Disabled
	public void add() {
		Category category = new Category("Trà sữa", null);
		assertNotNull(service.add(category));
	}

	@Test
	@Disabled
	public void findProductByCategory() {
		List<Product> products = service.findProductByCategory("Trà sữa");
		products.forEach(System.out::println);
	}

	@Test
	@Disabled
	public void update() {
		Category category = service.findById("Trà sữa");
		if (category != null) {
			category.setImage("new_image.jpg");
			assertNotNull(service.update(category));
		}
	}

	@Test
//	@Disabled
	public void remove() {
		String categoryId = "Cafe";
		assertTrue(service.remove(categoryId));
	}

	@Test
	@Disabled
	public void findById() {
		String categoryId = "Trà sữa";
		assertNotNull(service.findById(categoryId));
	}

	@Test
	@Disabled
	public void findAll() {
		List<Category> categories = service.findAll();
		categories.forEach(System.out::println);
	}
}
