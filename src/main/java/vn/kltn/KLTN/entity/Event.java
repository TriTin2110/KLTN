package vn.kltn.KLTN.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Event {
	@Id
	private String name;
	private Date startDate, endDate;

	@OneToMany(mappedBy = "event", cascade = { CascadeType.MERGE })
	private List<Product> products;

	public Event() {
	}

	public Event(String name, Date startDate, Date endDate) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.products = new ArrayList<Product>();
	}

	public Event(String name, Date startDate, Date endDate, List<Product> products) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.products = products;
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

	public void addProduct(Product product) {
		this.products.add(product);
		product.setEvent(this);
	}

	@Override
	public String toString() {
		return "Event [name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
