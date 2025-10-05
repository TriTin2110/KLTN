package vn.kltn.KLTN.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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

	@Bean
	public SecurityFilterChain creatChain(HttpSecurity http) {
		try {
			http.authorizeHttpRequests(auth -> auth
					.requestMatchers("/user/sign-in", "/user/sign-up", "/user/verify-code", "/user/reset-password", "/",
							"/css/**", "/fonts/**", "/images/**", "/js/**", "/plugins/**")
					.permitAll().anyRequest().authenticated())
					.formLogin(form -> form.loginPage("/user/sign-in").loginProcessingUrl("/authenticateTheUser")
							.defaultSuccessUrl("/user/sign-in-success", true).failureUrl("/user/sign-in?error=true")
							.permitAll())
					.csrf(csrf -> csrf.disable());
			return http.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
