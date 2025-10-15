package vn.kltn.KLTN.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.kltn.KLTN.entity.User;

@ControllerAdvice
public class GlobalControllerAdvice {
	@ModelAttribute("user")
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			return (User) authentication.getPrincipal();
		}
		return null;
	}
}
