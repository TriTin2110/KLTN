package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Chat;

public interface ChatService {
	public Chat findById(int id);

	public Chat findByName(String name);

	public List<Chat> findAll();

	public List<Chat> updateCache();

}
