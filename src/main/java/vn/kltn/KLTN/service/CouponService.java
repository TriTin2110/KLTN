package vn.kltn.KLTN.service;

import java.time.LocalDate;
import java.util.List;

import vn.kltn.KLTN.entity.Coupon;
import vn.kltn.KLTN.entity.Event;

public interface CouponService {
	public boolean add(Coupon coupon);

	public boolean remove(String id);

	public boolean update(Coupon coupon);

	public Coupon findById(String couponId);

	public List<Coupon> findAll();

	public boolean updateEvent(Coupon coupon, Event event);

	public void checkQueueEventStatus(LocalDate date);

	public void checkOnGoingEventStatus(LocalDate date);
}
