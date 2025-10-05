package vn.kltn.KLTN.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import vn.kltn.KLTN.service.UserService;

@Configuration
public class UserSecurity {

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider createDaoAuthenticationProvider(UserService userService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userService);
		dao.setPasswordEncoder(passwordEncoder);
		return dao;
	}

}
