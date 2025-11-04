package vn.kltn.KLTN.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import vn.kltn.KLTN.modules.chat.ChatHandShake;
import vn.kltn.KLTN.modules.chat.ChatRoomHanler;
import vn.kltn.KLTN.modules.order.OrderHandler;

@Configuration
@EnableAsync // bật hỗ trợ xử lý bất đồng bộ để có thể lưu tin nhắn vào DB và gửi tin nhắn
				// song song
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	private ChatRoomHanler chatRoomHanler;
	private OrderHandler orderHandler;
	private ChatHandShake chatHandShake;

	@Autowired
	public WebSocketConfig(ChatRoomHanler chatRoomHanler, ChatHandShake chatHandShake, OrderHandler orderHandler) {
		this.chatHandShake = chatHandShake;
		this.chatRoomHanler = chatRoomHanler;
		this.orderHandler = orderHandler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(chatRoomHanler, "/chat").addInterceptors(chatHandShake)
				.addHandler(orderHandler, "/order-websocket").addInterceptors(chatHandShake); // Đăng ký
		// websocket và thêm
		// Interceptors để xử lý
		// request
	}

}
