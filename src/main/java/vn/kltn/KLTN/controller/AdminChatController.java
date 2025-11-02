package vn.kltn.KLTN.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.kltn.KLTN.entity.Chat;
import vn.kltn.KLTN.service.ChatService;

@Controller
@RequestMapping("/admin/chat")
public class AdminChatController {
	private ChatService chatService;

	@Autowired
	public AdminChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping("")
	public String showList(Model model) {
		List<Chat> chatList = chatService.findAll();
		model.addAttribute("chatList", chatList);
		return "admin/chat/chat";
	}
}
