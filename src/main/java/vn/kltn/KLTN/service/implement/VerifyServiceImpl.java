package vn.kltn.KLTN.service.implement;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import vn.kltn.KLTN.service.VerifyService;

@Service
public class VerifyServiceImpl implements VerifyService {
	private Map<String, String> verifyPool;
	private JavaMailSender mailSender;
	private final String FROM = "t13524357@gmail.com";
	private final String SUBJECT = "Mã xác thực từ Aunes";

	@Autowired
	public VerifyServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
		this.verifyPool = new HashMap<String, String>();
	}

	@Override
	public void sendMail(String to) {
		// TODO Auto-generated method stub
		Random random = new Random();
		int result = random.nextInt(1000, 10000);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(FROM);
		mailMessage.setTo(to);
		mailMessage.setSubject(SUBJECT);
		mailMessage.setText("Mã xác thực của bạn là: " + result);
		try {
			mailSender.send(mailMessage);
			verifyPool.put(to, String.valueOf(result));
			System.out.println(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkVerifyCode(String email, String code) {
		// TODO Auto-generated method stub
		if (!verifyPool.containsKey(email))
			return false;
		if (!verifyPool.get(email).equals(code))
			return false;
		return true;
	}

}
