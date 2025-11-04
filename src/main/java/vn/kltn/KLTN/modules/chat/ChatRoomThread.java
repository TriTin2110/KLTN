package vn.kltn.KLTN.modules.chat;

import org.springframework.stereotype.Component;

import vn.kltn.KLTN.entity.Chat;
import vn.kltn.KLTN.entity.Message;
import vn.kltn.KLTN.repository.MessageRepository;

//Mỗi lần có tin nhắn mới thì sẽ tạo ra 1 thread để chịu trách nhiệm việc lưu tin nhắn xuống database
@Component
public class ChatRoomThread extends Thread {
	private MessageRepository chatRepository;
	private Chat chat;
	private Message message;

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
