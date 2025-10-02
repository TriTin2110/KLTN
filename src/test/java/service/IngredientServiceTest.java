package service;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
import vn.kltn.KLTN.service.IngredientService;
import vn.kltn.KLTN.service.SupplierService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class IngredientServiceTest {

	@Autowired
	private IngredientService service;
	@Autowired
	private SupplierService supplierService;

	@Test
	@Disabled
	public void add() {
		Supplier supplier = supplierService.findById("Trung Nguyên");
//		Supplier supplier = new Supplier("Vingroup", "Việt Nam", 123123121);
		Ingredient ingredient = new Ingredient("Hạt Cafe", 100, 10000);
		assertFalse(service.add(ingredient));
	}

	@Test
	@Disabled
	public void remove() {
		assertFalse(service.remove("Hạt Cafe"));
	}

	@Test
	@Disabled
	public void update() {
		Ingredient ingredient = service.findById("Thạch");
		System.out.println(ingredient);
		ingredient.setPrice(8000);
		ingredient = service.update(ingredient);
		System.out.println(ingredient);
	}

	@Test
	public void findAll() {
		List<Ingredient> ingredients = service.findAll();
		ingredients.forEach(System.out::println);
	}
}
