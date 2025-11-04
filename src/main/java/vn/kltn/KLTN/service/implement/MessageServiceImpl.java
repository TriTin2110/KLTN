package vn.kltn.KLTN.service.implement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Chat;
import vn.kltn.KLTN.entity.Message;
import vn.kltn.KLTN.repository.MessageRepository;
import vn.kltn.KLTN.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	private MessageRepository messageRepository;

	@Autowired
	public MessageServiceImpl(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Override
	@Transactional
	@Async
	public void saveAndFlushChat(Chat chat, String textMessage, String role, LocalDateTime time) {
		Message message = new Message(textMessage, role);
		try {
			message.setCreatedAt(time);
			message.setChat(new Chat(chat.getId()));
			messageRepository.saveAndFlush(message);
			System.out.println("Lưu tin nhắn thành công: " + message.getContent());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Đã có lỗi khi lưu tin nhắn: " + message.getContent() + "...");
		}
	}

	@Override
	public List<Message> findByChat(int id) {
		// TODO Auto-generated method stub
		return this.messageRepository.findByChat_Id(id);
	}

}
