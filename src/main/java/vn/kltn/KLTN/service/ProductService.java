package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Product;

public interface ProductService {
	public boolean add(Product product);

	public boolean remove(String productName);

	public boolean update(Product product);

	public Product findById(String productName);

	public List<Product> findAll();
}
