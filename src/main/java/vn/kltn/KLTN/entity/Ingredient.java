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
	private String quantity;
	private long price;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinTable(name = "product_ingredient", joinColumns = @JoinColumn(name = "ingredient_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> products;

	public Ingredient() {
	}

	public Ingredient(String name, String quantity, long price) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.products = new ArrayList<Product>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
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

	public void addSupplier(Supplier supplier) {
		this.supplier = supplier;
		supplier.getIngredients().add(this);
	}

	@Override
	public String toString() {
		return "Ingredient [name=" + name + ", quantity=" + quantity + ", price=" + price + ", supplier=" + supplier
				+ "]";
	}

}
