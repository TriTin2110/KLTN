package vn.kltn.KLTN.model;

public class CartItemDTO {
	private String productId, productImage, size;
	private int quantity, price;

	public CartItemDTO() {
		super();
	}

	public CartItemDTO(String productId, String productImage, String size, int quantity, int price) {
		super();
		this.productId = productId;
		this.productImage = productImage;
		this.size = size;
		this.quantity = quantity;
		this.price = price;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
