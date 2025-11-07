package vn.kltn.KLTN.enums;

public enum ProductStatus {
	RUN_OUT("Đã hết"), STILL("Còn hàng");

	private String label;

	private ProductStatus(String lable) {
		this.label = lable;
	}

	public String getLabel() {
		return label;
	}

}
