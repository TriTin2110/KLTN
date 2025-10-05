package service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.kltn.KLTN.KltnApplication;
import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.Point;
import vn.kltn.KLTN.entity.Role;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.enums.RoleAvailable;
import vn.kltn.KLTN.service.RoleService;
import vn.kltn.KLTN.service.UserService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceTest {

	@Autowired
	private UserService service;
	@Autowired
	private RoleService roleService;

	@Test
//	@Disabled
	public void adding() {
		Role role = roleService.findByType(RoleAvailable.ROLE_USER);
		Cart cart = new Cart("tinnguyen");
		Order order = new Order("tinnguyen" + System.currentTimeMillis());
		Point point = new Point("tinnguyen");
		User user = new User("tinnguyen", "123", "tin@gmail.com", "123 Quận 1", "123123123", role);

		user.addCart(cart);
		cart.addOrder(order);
		point.addOrder(order);
		point.addUser(user);

		user = service.signUp(user);
		assertNotNull(user);
	}

	@Test
	@Disabled
	public void signIn() {
		assertNull(service.signIn("tinnguyen123", "1234"));
	}

	@Test
	@Disabled
	public void remove() {
		assertTrue(service.delete("tinnguyen"));
	}

	@Test
	@Disabled
	public void update() {
		User user = service.findById("tinnguyen123");
		System.out.println(user);
		user.setFullName("Nguyễn Trí Tín");
		System.out.println(service.update(user));

	}

	@Test
	@Disabled
	public void findAll() {
		List<User> users = service.findAll();
		users.forEach(System.out::println);
	}
}
