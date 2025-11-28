package vn.kltn.KLTN.configuration;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import vn.kltn.KLTN.service.CouponService;
import vn.kltn.KLTN.service.EventService;

@Configuration
@EnableScheduling
public class ScheduleConfig {
	private final int TIME_OUT = 60000;

	@Autowired
	private EventService eventService;
	@Autowired
	private CouponService couponService;

	@Scheduled(fixedRate = TIME_OUT) // Chạy mỗi phút
	public void checkDiscountEvent() {
		LocalDate date = LocalDate.now();
		eventService.checkQueueEventStatus(date);
		eventService.checkOnGoingEventStatus(date);
		couponService.checkOnGoingEventStatus(date);
		couponService.checkQueueEventStatus(date);
	}
}
