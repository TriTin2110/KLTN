package vn.kltn.KLTN.modules;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.kltn.KLTN.entity.Notification;

@Component
public class NoticeHandler extends TextWebSocketHandler {
	private final String USER_ID = "userId";
	private ObjectMapper om;
	private static final Set<WebSocketSession> userSession = ConcurrentHashMap.newKeySet(); // (Danh sách tất cả người
																							// dùng đang hoạt động) An
																							// toàn trong môi trường đa
																							// luồng

	@Autowired
	public NoticeHandler(ObjectMapper om) {
		this.om = om;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		if (!userSession.contains(session)) {
			System.out.println(getUserId(session) + " đã tham gia!");
			userSession.add(session);
		}
	}

	public void updateNotificationSocket(Notification notification) throws JsonProcessingException, IOException {
		System.out.println("notification.getUserId(): " + notification.getUserId());
		for (WebSocketSession webSocketSession : userSession) {
			System.out.println(getUserId(webSocketSession));
			if (notification.getUserId().equals(getUserId(webSocketSession))) {
				System.out.println("Da them moi notification");
				webSocketSession.sendMessage(new TextMessage(om.writeValueAsString(notification)));
				return;
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(getUserId(session) + " đã thoát!");
		userSession.remove(session);
	}

	private String getUserId(WebSocketSession session) {
		return (String) session.getAttributes().get(USER_ID); // Lấy user_id từ HandShake
	}
}
