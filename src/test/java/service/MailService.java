package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.kltn.KLTN.KltnApplication;

@SpringBootTest(classes = KltnApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class MailService {
	@Autowired
	private vn.kltn.KLTN.service.VerifyService mailService;

	@Test
	public void testSendMail() {
		mailService.sendMail("tinnguyen5071@gmail.com");
	}
}
