package vn.kltn.KLTN.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Category extends Component {
	private int totalProduct;

	@OneToMany(mappedBy = "category")
	private List<Product> products;

	public Category() {
	}

	public Category(String name, String image, int totalProduct, List<Product> products) {
		super(name, image);
		this.totalProduct = totalProduct;
		this.products = products;
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

}
