package vn.kltn.KLTN.model;

public class CartItemQuantityRequest {
	private String itemId;
	private int quantity;

	public CartItemQuantityRequest() {
	}

	public CartItemQuantityRequest(String itemId, int quantity) {
		this.itemId = itemId;
		this.quantity = quantity;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartItemQuantityRequest [itemId=" + itemId + ", quantity=" + quantity + "]";
	}

}
