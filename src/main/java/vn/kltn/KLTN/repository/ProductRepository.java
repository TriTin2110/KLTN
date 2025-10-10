package vn.kltn.KLTN.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.kltn.KLTN.entity.Product;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, String> {
	@EntityGraph(attributePaths = { "ingredients", "ingredients.supplier", "category", "productDetail" }) // Khi chạy
																											// query
																											// này, hãy
																											// join
	// và load
	// luôn quan hệ ingredients, suppliers, category, productDetail của
	// Product
	public List<Product> findAll();
}
