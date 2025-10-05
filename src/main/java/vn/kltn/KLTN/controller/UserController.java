package vn.kltn.KLTN.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.kltn.KLTN.entity.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@GetMapping("/sign-up")
	public String showSignUpPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "sign-up";
	}

	@PostMapping("/sign-up")
	public void signUp() {
	}
}
