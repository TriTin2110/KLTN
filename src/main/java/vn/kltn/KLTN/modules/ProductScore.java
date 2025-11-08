package vn.kltn.KLTN.modules;
//Dùng trong thuật toán tìm kiếm (để sắp xếp hiển thị sản phẩm theo độ tương đồng)

import vn.kltn.KLTN.entity.Product;

public class ProductScore {
	private double score;
	private Product product;

	public ProductScore() {
	}

	public ProductScore(double score, Product product) {
		this.score = score;
		this.product = product;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
