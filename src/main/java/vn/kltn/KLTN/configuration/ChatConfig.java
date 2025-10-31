package vn.kltn.KLTN.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import vn.kltn.KLTN.service.ChatService;

@Configuration
@EnableWebSocket
public class ChatConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(getChatService(), "/chat"); // Đăng ký ChatService trên websocket
	}

	public ChatService getChatService() {
		return new ChatService();
	}
}
