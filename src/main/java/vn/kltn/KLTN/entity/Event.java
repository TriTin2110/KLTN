package vn.kltn.KLTN.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Event {
	@Id
	private String name;
	private Date startDate, endDate;

	@OneToMany(mappedBy = "event")
	private List<Product> products;
	@OneToMany(mappedBy = "event")
	private List<Coupon> coupons;

	public Event() {
	}

	public Event(String name, Date startDate, Date endDate, List<Product> products, List<Coupon> coupons) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.products = products;
		this.coupons = coupons;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

}
