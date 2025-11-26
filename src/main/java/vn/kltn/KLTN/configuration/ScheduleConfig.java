package vn.kltn.KLTN.configuration;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import vn.kltn.KLTN.service.EventService;

@Configuration
@EnableScheduling
public class ScheduleConfig {
	@Autowired
	private EventService eventService;

	@Scheduled(fixedRate = 60000) // Chạy mỗi phút
	public void checkDiscountEvent() {
		LocalDate date = LocalDate.now();
		eventService.checkQueueEventStatus(date);

		eventService.checkOnGoingEventStatus(date);
	}
}
