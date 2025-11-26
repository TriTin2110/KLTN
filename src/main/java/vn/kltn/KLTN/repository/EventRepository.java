package vn.kltn.KLTN.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.kltn.KLTN.entity.Event;
import vn.kltn.KLTN.enums.EventStatus;

@RepositoryRestResource
public interface EventRepository extends JpaRepository<Event, String> {

	public List<Event> findByStartDateAndEventStatus(LocalDate date, EventStatus eventStatus);

	public List<Event> findByEndDateAndEventStatus(LocalDate date, EventStatus eventStatus);
}
