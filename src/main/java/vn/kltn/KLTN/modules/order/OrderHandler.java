package vn.kltn.KLTN.modules.order;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.kltn.KLTN.entity.Order;

@Component
public class OrderHandler extends TextWebSocketHandler {
	private final String ROLE = "role", USER_ID = "userId", ROLE_EMPLOYEE = "employee";
	private final ObjectMapper om = new ObjectMapper();
	private static final Set<WebSocketSession> employeeSession = ConcurrentHashMap.newKeySet(); // An toàn trong môi
																								// trường đa luồng

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		if (ROLE_EMPLOYEE.equals(getRole(session))) {
			System.out.println("Nhân viên " + getUserId(session) + " đã tham gia!");
			employeeSession.add(session);
		}
	}

	public void broadcastOrder(Order order) throws IOException {
		String json = om.writeValueAsString(order);
		for (WebSocketSession webSocketSession : employeeSession) {
			webSocketSession.sendMessage(new TextMessage(json.getBytes()));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Nhân viên " + getUserId(session) + " đã thoát!");
		employeeSession.remove(session);
	}

	private String getRole(WebSocketSession session) {
		return (String) session.getAttributes().get(ROLE); // Lấy role từ HandShake
	}

	private String getUserId(WebSocketSession session) {
		return (String) session.getAttributes().get(USER_ID); // Lấy user_id từ HandShake
	}
}
