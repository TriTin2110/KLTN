package vn.kltn.KLTN.entity;

public enum ProductStatus {
    IN_STOCK("Còn hàng"),
    OUT_OF_STOCK("Hết hàng");

    private final String label;

    ProductStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
