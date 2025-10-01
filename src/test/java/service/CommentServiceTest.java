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
import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.service.CommentService;
import vn.kltn.KLTN.service.ProductService;
import vn.kltn.KLTN.service.UserService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CommentServiceTest {

	@Autowired
	private CommentService service;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;

	@Test
//	@Disabled
	public void add() {
		Product product = productService.findById("Trà sữa truyền thống");
		User user = userService.findById("tinnguyen");
		long time = System.currentTimeMillis();
		Comment comment = new Comment(user.getUserName() + "-" + time, "So Bad!", new Date(time), product, user);
		assertTrue(service.add(comment));
	}

	@Test
	@Disabled
	public void remove() {
		String id = "tinnguyen1231758954991187";
		assertTrue(service.remove(id));
	}

	@Test
	@Disabled
	public void findAllByProductId() {
		List<Comment> comments = service.findAllByProductId("Trà sữa truyền thống");
		comments.forEach(System.out::println);
	}

	@Test
	@Disabled
	public void findAll() {
		List<Comment> comments = service.findAll();
		comments.forEach(System.out::println);
	}
}
