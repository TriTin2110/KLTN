package vn.kltn.KLTN.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import vn.kltn.KLTN.enums.EventStatus;

@Entity
public class Coupon {
	@Id
	private String id;
	private String description;
	private short discountRate;
	private int score;
	private LocalDate startAt, endAt;
	@Enumerated(EnumType.STRING)
	private EventStatus eventStatus;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	public Coupon() {
	}

	public Coupon(String id, short discountRate, LocalDate endAt) {
		this.id = id;
		this.discountRate = discountRate;
		this.endAt = endAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public short getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(short discountRate) {
		this.discountRate = discountRate;
	}

	public LocalDate getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalDate endAt) {
		this.endAt = endAt;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EventStatus getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}

	public LocalDate getStartAt() {
		return startAt;
	}

	public void setStartAt(LocalDate startAt) {
		this.startAt = startAt;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", description=" + description + ", discountRate=" + discountRate + ", startAt="
				+ startAt + ", endAt=" + endAt + "]";
	}

}
