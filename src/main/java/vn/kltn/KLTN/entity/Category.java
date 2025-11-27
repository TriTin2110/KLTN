package vn.kltn.KLTN.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import vn.kltn.KLTN.enums.EventStatus;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Category extends Component {
	private int totalProduct;
	private EventStatus eventStatus;
	@OneToMany(mappedBy = "category", cascade = { CascadeType.REMOVE, CascadeType.MERGE })
	@JsonIgnore
	private List<Product> products;

	public Category() {
	}

	public Category(String name, String image) {
		super(name, image);
		this.totalProduct = 0;
		this.products = new ArrayList<Product>();
	}

	public int getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(int totalProduct) {
		this.totalProduct = totalProduct;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void addProduct(Product product) {
		this.products.add(product);
		this.totalProduct = this.products.size();
		product.setCategory(this);
	}

	public EventStatus getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}

	@Override
	public String toString() {
		return "Category [totalProduct=" + totalProduct + ", getName()=" + getName() + "]";
	}

}
