package vn.kltn.KLTN.enums;

public enum EventStatus {
	NONE("Không nằm trong event"), ON_QUEUE("Đang trong hàng đợi!"), IS_GOING_ON("Đang diễn ra"), END("Đã kết thúc");

	private String label;

	private EventStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
