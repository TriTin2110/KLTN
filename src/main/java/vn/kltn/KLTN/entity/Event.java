package vn.kltn.KLTN.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import vn.kltn.KLTN.enums.EventStatus;
import vn.kltn.KLTN.model.CategoryItemDTO;

@Entity
public class Event {
	@Id
	private String name;
	private String description, image;
	private LocalDate startDate, endDate;
	@Enumerated(EnumType.STRING)
	private EventStatus eventStatus;
	@Transient
	private List<CategoryItemDTO> items;

	@OneToMany(mappedBy = "event", cascade = { CascadeType.MERGE })
	private List<Product> products;

	public Event() {
		this.products = new ArrayList<Product>();
	}

	public Event(String name, LocalDate startDate, LocalDate endDate) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.products = new ArrayList<Product>();
	}

	public Event(String name, LocalDate startDate, LocalDate endDate, List<Product> products) {
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<CategoryItemDTO> getItems() {
		return items;
	}

	public void setItems(List<CategoryItemDTO> items) {
		this.items = items;
	}

	public EventStatus getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}

	@Override
	public String toString() {
		return "Event [name=" + name + ", description=" + description + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}

}
