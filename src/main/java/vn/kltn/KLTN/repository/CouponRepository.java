package vn.kltn.KLTN.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.kltn.KLTN.entity.Coupon;
import vn.kltn.KLTN.enums.EventStatus;

@RepositoryRestResource
public interface CouponRepository extends JpaRepository<Coupon, String> {
	public List<Coupon> findByStartAtAndEventStatus(LocalDate date, EventStatus eventStatus);

	public List<Coupon> findByEndAtAndEventStatus(LocalDate date, EventStatus eventStatus);
}
