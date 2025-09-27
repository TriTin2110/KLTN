package vn.kltn.KLTN.entity;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Coupon {
	@Id
	private String id;
	private short discountRate;
	private Date endAt;

	@OneToOne(mappedBy = "coupon", cascade = CascadeType.MERGE)
	private Product product;
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	public Coupon() {
	}

	public Coupon(String id, short discountRate, Date endAt, Product product, Event event) {
		this.id = id;
		this.discountRate = discountRate;
		this.endAt = endAt;
		this.product = product;
		this.event = event;
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

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", discountRate=" + discountRate + ", endAt=" + endAt + "]";
	}

}
