package vn.kltn.KLTN.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Ingredient {
	@Id
	private String name;
	private int quantity, price;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "product_ingredient", joinColumns = @JoinColumn(name = "ingredient_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> products;

	public Ingredient() {
	}

	public Ingredient(String name, int quantity, int price, Supplier supplier) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.supplier = supplier;
		this.products = new ArrayList<Product>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Ingredient [name=" + name + ", quantity=" + quantity + ", price=" + price + ", supplier=" + supplier
				+ ", products=" + products + "]";
	}

}
