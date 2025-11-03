package vn.kltn.KLTN.modules.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.kltn.KLTN.entity.Chat;
import vn.kltn.KLTN.service.ChatService;
import vn.kltn.KLTN.service.MessageService;

@Component
public class ChatRoomHanler extends TextWebSocketHandler {
	private final String ROLE = "role", USER_ID = "userId";
	private static Map<WebSocketSession, Chat> userList = new HashMap<WebSocketSession, Chat>();
	private static Map<WebSocketSession, Chat> employeeList = new HashMap<WebSocketSession, Chat>();
	private static List<Chat> chatRooms = new ArrayList<Chat>();
	private ChatService chatService;
	private MessageService messageService;

	@Autowired
	public ChatRoomHanler(ChatService chatService, MessageService messageService) {
		this.chatService = chatService;
		this.messageService = messageService;
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
		// TODO Auto-generated method stub
		String role = textMessage.getPayload();
		if (role.contains("\"type\":\"join-room\"")) { // Kiểm tra xem nhân viên có chọn phòng không
			chatRooms = chatService.findAll();
			ObjectMapper om = new ObjectMapper();
			JsonNode node = om.readTree(role);
			int chatId = node.get("chatId").asInt(); // Lấy id phòng mà nhân viên đã chọn
			Chat chatRoom = chatRooms.stream().filter(o -> o.getId() == chatId).findFirst().get();
			employeeList.put(session, chatRoom); // Gán phiên làm việc của nhân viên vào phòng này
			return;
		}
		saveMessage(session, textMessage.getPayload());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		String role = getRole(session);
		if ("user".equals(role)) { // Sau khi kết nối của user được thành lập
			chatRooms = chatService.findAll();
			String userId = getUserId(session);
			Chat chat = chatRooms.stream().filter(o -> userId.equals(o.getName())).findFirst().get();
			userList.put(session, chat); // Gán cho phiên làm việc của user vào phòng này
			System.out.println("Khách hàng " + userId + " đã kết nối!");
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		String role = getRole(session);
		String userId = getUserId(session);
		if ("user".equals(role)) {
			userList.remove(session);
		} else {
			employeeList.remove(session);
		}
		System.out.println(userId + " đã thoát!");
	}

	private void saveMessage(WebSocketSession session, String textMessage) {
		String role = getRole(session);
		Chat chat = null;
		if ("user".equals(role)) {// khi gửi tin nhắn thì sẽ lấy phòng của người gửi
			chat = userList.get(session);
		} else {

			chat = employeeList.get(session);
		}
		messageService.saveAndFlushChat(chat, textMessage, role);
		showMessage(textMessage, role, chat.getId());// hiển thị tin nhắn cho người nhận
	}

	private void showMessage(String textMessage, String role, int chatRoomId) {
		Map<WebSocketSession, Chat> targetMap = ("user".equals(role)) ? employeeList : userList;
		targetMap.forEach((session, chat) -> {
			if (session != null && session.isOpen() && chat != null && chatRoomId == chat.getId()) {
				try {
					session.sendMessage(new TextMessage(textMessage.getBytes()));
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private String getRole(WebSocketSession session) {
		return (String) session.getAttributes().get(ROLE); // Lấy role từ ChatHandShake
	}

	private String getUserId(WebSocketSession session) {
		return (String) session.getAttributes().get(USER_ID); // Lấy user_id từ ChatHandShake
	}

}
