package service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.kltn.KLTN.KltnApplication;
import vn.kltn.KLTN.entity.Event;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.service.EventService;
import vn.kltn.KLTN.service.ProductService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class EventServiceTest {
	@Autowired
	private EventService service;
	@Autowired
	private ProductService productService;

	@Test
//	@Disabled
	public void add() {
		Product product = productService.findById("Cafe đá");
		String name = "Mùa hè siêu ưu đãi!";
		Date startDate = new Date(2025 - 1900, 6 - 1, 3);
		Date endDate = new Date(2025 - 1900, 8 - 1, 15);
		Event event = new Event(name, startDate, endDate);

		event.addProduct(product);

		assertTrue(service.add(event));

	}

	@Test
	@Disabled
	public void findAll() {
		service.findAll();
	}

	@Test
	@Disabled
	public void findProduct() {
		productService.findById("Trà sữa matcha");
	}

	@Test
	@Disabled
	public void remove() {
		String name = "Mùa hè siêu ưu đãi!";
		assertTrue(service.remove(name));
	}

	@Test
	@Disabled
	public void update() {
		String name = "Mùa hè siêu ưu đãi!";
		Event event = service.findById(name);
		Date startDate = new Date(2025 - 1900, 5 - 1, 3);
		event.setStartDate(startDate);
		assertTrue(service.update(event));

	}
}
