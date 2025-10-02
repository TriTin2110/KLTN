package service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.kltn.KLTN.KltnApplication;
import vn.kltn.KLTN.entity.Coupon;
import vn.kltn.KLTN.entity.Event;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.service.CouponService;
import vn.kltn.KLTN.service.EventService;
import vn.kltn.KLTN.service.ProductService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CouponTest {
	@Autowired
	private CouponService service;
	@Autowired
	private ProductService productService;
	@Autowired
	private EventService eventService;

	@Test
	@Disabled
	public void add() {
		long time = System.currentTimeMillis();
		Product product = productService.findById("Cafe đá");
		Coupon coupon = new Coupon(product.getName() + " - " + time, (short) 0, new Date(time), product);
		product.addCoupon(coupon);
		assertTrue(service.add(coupon));
	}

	@Test
//	@Disabled
	public void remove() {
		String name = "Cafe đá - 1759313762558";
		assertTrue(service.remove(name));
	}

	@Test
	@Disabled
	public void update() {
		Coupon coupon = service.findById("Trà sữa matcha - 1758976052746");
		Event event = eventService.findById("Mùa hè siêu ưu đãi!");
		assertTrue(service.update(coupon));
	}

	@Test
	@Disabled
	public void findAll() {
		List<Coupon> coupons = service.findAll();
		coupons.forEach(System.out::println);
	}
}
