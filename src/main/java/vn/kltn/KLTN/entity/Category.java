package vn.kltn.KLTN.entity;

import java.util.ArrayList;
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

	@Override
	public String toString() {
		return "Category [totalProduct=" + totalProduct + ", getName()=" + getName() + "]";
	}

}
