package vn.kltn.KLTN.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

@Entity
public class ProductDetail {
	@Id
	private String name;
	@Lob
	private String description;
	private String status;
	@OneToOne(mappedBy = "productDetail")
	private Product product;

	public ProductDetail() {
	}

	public ProductDetail(String name, String description, String status, Product product) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "ProductDetail [name=" + name + ", description=" + description + ", status=" + status + "]";
	}

}
