package vn.kltn.KLTN.service.implement;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Coupon;
import vn.kltn.KLTN.entity.Event;
import vn.kltn.KLTN.enums.EventStatus;
import vn.kltn.KLTN.repository.CouponRepository;
import vn.kltn.KLTN.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {
	@Autowired
	private CouponRepository repository;

	@Override
	@Transactional
	public boolean add(Coupon coupon) {
		// TODO Auto-generated method stub
		try {
			repository.save(coupon);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public boolean remove(String id) {
		// TODO Auto-generated method stub
		try {
			Coupon coupon = findById(id);
			if (coupon != null) {
				repository.delete(coupon);
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
	public boolean update(Coupon coupon) {
		// TODO Auto-generated method stub
		try {
			repository.saveAndFlush(coupon);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Coupon findById(String couponId) {
		// TODO Auto-generated method stub
		Optional<Coupon> opt = repository.findById(couponId);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	public List<Coupon> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Transactional
	public boolean updateEvent(Coupon coupon, Event event) {
		try {
			if (findById(coupon.getId()) != null) {
//				coupon.setEvent(event);
				repository.saveAndFlush(coupon);
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
	public void checkQueueEventStatus(LocalDate date) {
		// TODO Auto-generated method stub
		System.out.println("đã thực hiện kiểm tra coupon (start)");
		List<Coupon> coupons = this.repository.findByStartAtAndEventStatus(date, EventStatus.ON_QUEUE);
		try {
			if (coupons != null && !coupons.isEmpty()) {
				for (Coupon coupon : coupons) {
					coupon.setEventStatus(EventStatus.IS_GOING_ON);
				}
				this.repository.saveAllAndFlush(coupons);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void checkOnGoingEventStatus(LocalDate date) {
		// TODO Auto-generated method stub
		System.out.println("đã thực hiện kiểm tra coupon (end)");
		List<Coupon> coupons = this.repository.findByEndAtAndEventStatus(date, EventStatus.IS_GOING_ON);
		try {
			if (coupons != null && !coupons.isEmpty()) {
				for (Coupon coupon : coupons) {
					coupon.setEventStatus(EventStatus.NONE);
				}
				this.repository.saveAllAndFlush(coupons);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
