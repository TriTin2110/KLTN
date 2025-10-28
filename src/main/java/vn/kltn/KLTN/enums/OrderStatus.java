package vn.kltn.KLTN.enums;

public enum OrderStatus {
	PENDING("Đang xử lý"), COMPLETED("Đã hoàn tất"), CANCELLED("Bị hủy");

	private String label;

	private OrderStatus(String label) {
		this.label = label;
	}

	public String getValue() {
		return this.label;
	}
}
