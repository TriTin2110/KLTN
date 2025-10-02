package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Coupon;
import vn.kltn.KLTN.entity.Event;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.repository.CouponRepository;
import vn.kltn.KLTN.service.CouponService;
import vn.kltn.KLTN.service.ProductService;

@Service
public class CouponServiceImpl implements CouponService {
	@Autowired
	private CouponRepository repository;
	@Autowired
	private ProductService productService;

	@Override
	@Transactional
	public boolean add(Coupon coupon) {
		// TODO Auto-generated method stub
		try {

			if (findById(coupon.getId()) == null) {
				if (coupon.getProduct() != null) {
					repository.save(coupon);
					return true;
				}
			}
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
				Product product = coupon.getProduct();
				product.setCoupon(null);
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
			if (findById(coupon.getId()) != null) {
				Product product = coupon.getProduct();
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
}
