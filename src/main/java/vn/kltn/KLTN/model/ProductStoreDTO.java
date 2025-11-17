package vn.kltn.KLTN.model;

import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.enums.ProductStatus;

public class ProductStoreDTO {
	private String name, image, category;
	private ProductStatus productStatus;
	private int price;

	public ProductStoreDTO() {
	}

	public ProductStoreDTO(String name, String image, String category, ProductStatus productStatus, int price) {
		this.name = name;
		this.image = image;
		this.category = category;
		this.productStatus = productStatus;
		this.price = price;
	}

	public ProductStoreDTO(Product product) {
		this.name = product.getName();
		this.image = product.getImage();
		this.category = product.getCategory().getName();
		this.productStatus = product.getProductStatus();
		this.price = product.getLowestPrice();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ProductStatus getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(ProductStatus productStatus) {
		this.productStatus = productStatus;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductStoreDTO [name=" + name + ", image=" + image + ", category=" + category + ", productStatus="
				+ productStatus + ", price=" + price + "]";
	}

}
