package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Chat;
import vn.kltn.KLTN.repository.ChatRepository;
import vn.kltn.KLTN.service.ChatService;
import vn.kltn.KLTN.service.UserService;

@Service
public class ChatServiceImpl implements ChatService {
	private ChatRepository chatRepository;
	private UserService userService;

	@Autowired
	public ChatServiceImpl(ChatRepository chatRepository, UserService userService) {
		this.chatRepository = chatRepository;
		this.userService = userService;
	}

	@Override
	public Chat findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Cacheable("chat")
	public List<Chat> findAll() {
		// TODO Auto-generated method stub
		return this.chatRepository.findAll();
	}

	@Override
	@CachePut("chat")
	public List<Chat> updateCache() {
		// TODO Auto-generated method stub
		return this.chatRepository.findAll();
	}

	@Override
	public Chat findById(int id) {
		// TODO Auto-generated method stub
		Optional<Chat> opt = this.chatRepository.findById(id);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	@Transactional
	@Async
	public void updateTime(Chat chat) {
		// TODO Auto-generated method stub
		this.chatRepository.updateTime(chat.getId(), chat.getDate());
	}

	@Override
	@Transactional
	public void update(Chat chat) {
		// TODO Auto-generated method stub
		this.chatRepository.saveAndFlush(chat);
	}

}
