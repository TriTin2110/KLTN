package vn.kltn.KLTN.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;

@Entity
public class Combo {
	@Id
	private String id;
	@Lob
	private String shortDescription;
	private int total;
	private short discount;

	@ManyToMany
	@JoinTable(name = "product_combo", joinColumns = @JoinColumn(name = "combo_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> products;

	public Combo() {
	}

	public Combo(String id, String shortDescription, int total, short discount, List<Product> products) {
		this.id = id;
		this.shortDescription = shortDescription;
		this.total = total;
		this.discount = discount;
		this.products = products;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public short getDiscount() {
		return discount;
	}

	public void setDiscount(short discount) {
		this.discount = discount;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
