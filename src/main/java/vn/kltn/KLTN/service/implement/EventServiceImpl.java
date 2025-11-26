package vn.kltn.KLTN.service.implement;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Event;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.enums.EventStatus;
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
			repository.saveAndFlush(event);
			return true;
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
				event.getProducts().forEach(o -> o.setEvent(null));
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

	@Override
	@Transactional
	public void checkQueueEventStatus(LocalDate date) {
		// TODO Auto-generated method stub
		try {
			System.out.println("đã thực hiện kiểm tra event (start)");
			List<Event> events = this.repository.findByStartDateAndEventStatus(date, EventStatus.ON_QUEUE);
			if (events != null && !events.isEmpty()) {
				List<Product> products = null;
				for (Event event : events) {
					products = event.getProducts();
					for (Product product : products) {
						product.setEventStatus(EventStatus.IS_GOING_ON);
					}
					event.setEventStatus(EventStatus.IS_GOING_ON);
					this.repository.saveAndFlush(event);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void checkOnGoingEventStatus(LocalDate date) {
		// TODO Auto-generated method stub
		try {
			System.out.println("đã thực hiện kiểm tra event (end)");
			List<Event> events = this.repository.findByEndDateAndEventStatus(date, EventStatus.IS_GOING_ON);
			if (events != null && !events.isEmpty()) {
				List<Product> products = null;
				for (Event event : events) {
					products = event.getProducts();
					for (Product product : products) {
						product.setEventStatus(EventStatus.END);
					}
					event.setEventStatus(EventStatus.END);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
