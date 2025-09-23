package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Coupon;

public interface CouponService {
	public boolean add(Coupon coupon);

	public boolean remove(String id);

	public boolean update(Coupon coupon);

	public Coupon findById(String couponId);

	public List<Coupon> findAll();
}
