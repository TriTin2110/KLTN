package vn.kltn.KLTN.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.entity.Event;
import vn.kltn.KLTN.entity.Product;

public interface ProductService {
	public List<Product> updateCache();

	public boolean add(Product product);

	public boolean remove(String productName);

	public boolean update(Product product);

	public Product findById(String productName);

	public List<Product> findAll();

	public List<Product> findByCategory(String categoryId);

	public boolean updateComment(Product product, Comment comment);

	public boolean updateEvent(Product product, Event event);

	public void insertAddtionInformation(Product product, String categoryId);

	public void addMultipleProductFromFile(Workbook workbook, IngredientService ingredientService,
			SupplierService supplierService);

}
