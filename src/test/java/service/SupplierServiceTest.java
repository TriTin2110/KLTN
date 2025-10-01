package service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.kltn.KLTN.KltnApplication;
import vn.kltn.KLTN.entity.Ingredient;
import vn.kltn.KLTN.entity.Supplier;
import vn.kltn.KLTN.service.SupplierService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SupplierServiceTest {
	@Autowired
	private SupplierService service;

	@Test
//	@Disabled
	public void add() {
		Supplier supplier = new Supplier("Trung Nguyên", "Phường Sài Gòn", 1231234123);
		List<Ingredient> ingredients = List.of(new Ingredient("Hạt Cafe", 100, 10, supplier));
		supplier.setIngredients(ingredients);
		assertTrue(service.add(supplier));
	}

	@Test
	@Disabled
	public void remove() {
		assertTrue(service.remove("Trung Nguyên"));
	}

	@Test
	@Disabled
	public void update() {
		Supplier supplier = service.findById("Vin");
		supplier.setAddress("New York");
		service.update(supplier);
	}

	@Test
	public void findAll() {
		List<Supplier> suppliers = service.findAll();
		suppliers.forEach(System.out::println);
	}
}
