package vn.kltn.KLTN.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {
	@GetMapping("/show-box")
	public String showChatBox() {
		return "chat-box";
	}
}
