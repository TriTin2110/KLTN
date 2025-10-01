package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Event;
import vn.kltn.KLTN.repository.EventRepository;
import vn.kltn.KLTN.service.CouponService;
import vn.kltn.KLTN.service.EventService;
import vn.kltn.KLTN.service.ProductService;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventRepository repository;
	@Autowired
	private ProductService productService;
	@Autowired
	private CouponService couponService;

	@Override
	@Transactional
	public boolean add(Event event) {
		// TODO Auto-generated method stub
		try {
			if (findById(event.getName()) == null) {
				repository.saveAndFlush(event);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public boolean remove(String name) {
		// TODO Auto-generated method stub
		try {
			Event event = findById(name);
			if (event != null) {
				repository.delete(event);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public boolean update(Event event) {
		// TODO Auto-generated method stub
		try {
			if (findById(event.getName()) != null) {
				repository.saveAndFlush(event);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Event findById(String eventId) {
		// TODO Auto-generated method stub
		Optional<Event> opt = repository.findById(eventId);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	public List<Event> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
