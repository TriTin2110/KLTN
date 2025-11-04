package vn.kltn.KLTN.service;

import java.time.LocalDateTime;
import java.util.List;

import vn.kltn.KLTN.entity.Chat;
import vn.kltn.KLTN.entity.Message;

public interface MessageService {
	public void saveAndFlushChat(Chat chat, String textMessage, String role, LocalDateTime time);

	public List<Message> findByChat(int id);
}
