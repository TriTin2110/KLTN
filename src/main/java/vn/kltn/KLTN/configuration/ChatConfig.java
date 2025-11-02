package vn.kltn.KLTN.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import vn.kltn.KLTN.modules.chat.ChatHandShake;
import vn.kltn.KLTN.modules.chat.ChatRoomHanler;

@Configuration
@EnableAsync // bật hỗ trợ xử lý bất đồng bộ để có thể lưu tin nhắn vào DB và gửi tin nhắn
				// song song
@EnableWebSocket
public class ChatConfig implements WebSocketConfigurer {
	private ChatRoomHanler chatRoomHanler;
	private ChatHandShake chatHandShake;

	@Autowired
	public ChatConfig(ChatRoomHanler chatRoomHanler, ChatHandShake chatHandShake) {
		this.chatHandShake = chatHandShake;
		this.chatRoomHanler = chatRoomHanler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(chatRoomHanler, "/chat").addInterceptors(chatHandShake); // Đăng ký ChatService trên
																						// websocket và thêm
																						// Interceptors để xử lý
																						// request
	}

}
