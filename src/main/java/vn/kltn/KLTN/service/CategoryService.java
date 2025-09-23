package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Category;
import vn.kltn.KLTN.entity.Product;

public interface CategoryService {
	public List<Product> findProductByCategory(String categoryId);

	public Category add(Category category);

	public Category update(Category category);

	public boolean remove(String categoryId);

	public Category findById(String categoryId);

	public List<Category> findAll();
}
