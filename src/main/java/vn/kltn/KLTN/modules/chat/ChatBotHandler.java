package vn.kltn.KLTN.modules.chat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.kltn.KLTN.service.VectorStoreSevice;

@Component
public class ChatBotHandler extends TextWebSocketHandler {
	@Autowired
	private VectorStoreSevice vectorStoreSevice;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
		// TODO Auto-generated method stub
		String userMessage = textMessage.getPayload();
		String result = getData(userMessage);
		Map<String, Object> payload = new HashMap<String, Object>();
		payload.putIfAbsent("message", result);
		String json = new ObjectMapper().writeValueAsString(payload);
		session.sendMessage(new TextMessage(json));
	}

	private String getData(String userMessage) {
		return vectorStoreSevice.search(userMessage);
	}
}
