package vn.kltn.KLTN.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import vn.kltn.KLTN.configuration.CaptchaConfig;
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
	public SecurityFilterChain creatChain(HttpSecurity http, CaptchaConfig captchaConfig) {
		try {
			http.addFilterBefore(captchaConfig, UsernamePasswordAuthenticationFilter.class)
					.authorizeHttpRequests(auth -> auth
							.requestMatchers("/user/profile",
									"/user/profile-update",
									"/user/change-password",
									"/cart/show",
									"/order/**")
							.authenticated().anyRequest().permitAll())
					.formLogin(form -> form.loginPage("/user/sign-in").loginProcessingUrl("/authenticateTheUser")
							.defaultSuccessUrl("/user/sign-in-success", true).failureUrl("/user/sign-in?error=true")
							.permitAll())
					.logout(logout -> logout.logoutSuccessUrl("/").invalidateHttpSession(true)
							.deleteCookies("JSESSIONID").permitAll())
					.csrf(csrf -> csrf.ignoringRequestMatchers("/logout").disable());
			return http.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
