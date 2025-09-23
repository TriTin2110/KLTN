package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Event;

public interface EventService {
	public boolean add(Event event);

	public boolean remove(String name);

	public boolean update(Event event);

	public Event findById(String eventId);

	public List<Event> findAll();
}
