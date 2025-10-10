package vn.kltn.KLTN.entity;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Coupon {
	@Id
	private String id;
	private short discountRate;
	private Date endAt;

	@OneToOne(mappedBy = "coupon", cascade = CascadeType.MERGE)
	private Product product;

	public Coupon() {
	}

	public Coupon(String id, short discountRate, Date endAt) {
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

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", discountRate=" + discountRate + ", endAt=" + endAt + "]";
	}

}
