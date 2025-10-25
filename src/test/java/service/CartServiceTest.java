package service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.kltn.KLTN.KltnApplication;
import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.service.CartService;
import vn.kltn.KLTN.service.UserService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CartServiceTest {

	private CartService service;
	private UserService userService;

	@Autowired
	public CartServiceTest(CartService service, UserService userService) {
		this.service = service;
		this.userService = userService;
	}

	@Test
	@Disabled
	public void addCart() {
		String username = "tinnguyen123";
		User user = userService.findById(username);
		Cart cart = new Cart(username);
		service.add(cart);
	}

	@Test
	@Disabled
	public void getCartByUsername() {
		String username = "tinnguyen123";
		Cart cart = service.findByUserName(username);
		System.out.println("Đây là cart: " + cart);
		assertNotNull(cart);
	}

	@Test
	@Disabled
	public void removeProduct() {
		String username = "tinnguyen123";
		String productName = "Trà sữa matcha";
		assertTrue(service.removeProductFromCart(username, productName));
	}

	@Test
//	@Disabled
	public void addProductToCart() {
		String userName = "tinnguyen", productName = "Cafe đá";
		int amount = 1;
//		assertNotNull(service.addProductToCart(userName, productName, amount));
	}

	@Test
	@Disabled
	public void updateAmount() {
		String userName = "tinnguyen123", productName = "Trà sữa truyền thống";
		int amount = 3;
//		assertNotNull(service.updateAmount(userName, productName, amount));
	}

	@Test
	@Disabled
	public void ordering() {
		User user = userService.findById("tinnguyen123");
		assertNotNull(service.ordering(user));
	}

}
