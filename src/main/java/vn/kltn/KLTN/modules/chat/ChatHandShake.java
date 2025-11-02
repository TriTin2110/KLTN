package vn.kltn.KLTN.modules.chat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class ChatHandShake implements HandshakeInterceptor {
	private final String ROLE = "role", USER_ID = "userId";

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		// TODO Auto-generated method stub
		try {
			Map<String, String> params = parseQueryParam(request.getURI().getQuery());
			String role = params.get(ROLE);
			String userId = params.get(USER_ID);
			attributes.put(ROLE, role);
			attributes.put(USER_ID, userId);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub

	}

	// Ví dụ: ws://localhost:8080/chat?role=customer&userId=customer123
	private Map<String, String> parseQueryParam(String query) {
		Map<String, String> params = new HashMap<String, String>();
		String[] kv = null;
		if (query != null && !query.isBlank()) {
			for (String pair : query.split("&")) { // Lấy từng cặp tham số
				kv = pair.split("=", 2); // Tách lấy phần key và values
				params.put(kv[0], kv[1]);
			}
		}
		return params;
	}

}
