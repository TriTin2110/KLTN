package vn.kltn.KLTN.service;

import vn.kltn.KLTN.entity.Chat;

public interface MessageService {
	public void saveAndFlushChat(Chat chat, String textMessage, String role);
}
