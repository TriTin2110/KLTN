package vn.kltn.KLTN.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.kltn.KLTN.entity.Message;
import vn.kltn.KLTN.service.ChatService;

@Controller
@RequestMapping("/chat")
public class ChatController {
	private ChatService chatService;

	@Autowired
	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping("/show-box")
	public String showChatBox() {
		return "chat-box";
	}

	@ResponseBody
	@GetMapping("/{id}")
	public List<Message> getContent(@PathVariable("id") int chatId) {
		List<Message> messsage = chatService.findMessage(chatId);
		return messsage;
	}
}
