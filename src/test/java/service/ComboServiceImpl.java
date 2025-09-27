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
import vn.kltn.KLTN.entity.Combo;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.service.ComboService;
import vn.kltn.KLTN.service.ProductService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ComboServiceImpl {
	@Autowired
	private ComboService service;
	@Autowired
	private ProductService productService;

	@Test
	@Disabled
	public void add() {
		Product product = productService.findById("Trà sữa truyền thống");
		Product product2 = productService.findById("Trà sữa matcha");
		System.out.println(service.saveAndUpdate(product, product2, "Mô tả ngắn", (short) 10));
	}

	@Test
	@Disabled
	public void remove() {
		assertTrue(service.remove("Trà sữa truyền thống-Trà sữa matcha"));
	}

	@Test
	public void findAll() {
		List<Combo> combos = service.findAll();
		combos.forEach(System.out::println);
	}
}
