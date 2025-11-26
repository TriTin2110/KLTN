package vn.kltn.KLTN.model;

public class CategoryItemDTO {
	private String categoryName;
	private int discount;

	public CategoryItemDTO() {
	}

	public CategoryItemDTO(String categoryName, int discount) {
		this.categoryName = categoryName;
		this.discount = discount;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "CategoryItemDTO [categoryName=" + categoryName + ", discount=" + discount + "]";
	}

}
