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
import vn.kltn.KLTN.entity.Role;
import vn.kltn.KLTN.enums.RoleAvailable;
import vn.kltn.KLTN.service.RoleService;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RoleServiceTest {
	@Autowired
	private RoleService service;

	@Test
	@Disabled
	public void add() {
		Role role = new Role(RoleAvailable.ROLE_ADMIN);
		assertNotNull(service.add(role));
	}

	@Test
	public void delete() {
		assertTrue(service.remove(RoleAvailable.ROLE_ADMIN));
	}
}
