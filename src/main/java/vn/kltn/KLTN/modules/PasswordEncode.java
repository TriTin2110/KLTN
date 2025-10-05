package vn.kltn.KLTN.modules;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncode {
	public static String encodePassword(String rawPassword) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		return bcrypt.encode(rawPassword);
	}
}
