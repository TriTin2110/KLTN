package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Category;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.repository.CategoryRepository;
import vn.kltn.KLTN.repository.ProductRepository;
import vn.kltn.KLTN.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository repository;
	@Autowired
	private ProductRepository productRepository;

	@Override
	@Transactional
	public Category add(Category category) {
		// TODO Auto-generated method stub
		try {
			if (findById(category.getName()) == null)
				return repository.save(category);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public Category update(Category category) {
		// TODO Auto-generated method stub
		try {
			if (findById(category.getName()) != null)
				return repository.saveAndFlush(category);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public boolean remove(String categoryId) {
		// TODO Auto-generated method stub
		try {
			Category category = findById(categoryId);
			if (category != null) {
				repository.delete(category);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Category findById(String categoryId) {
		// TODO Auto-generated method stub
		Optional<Category> optCategory = repository.findById(categoryId);
		return (optCategory.isEmpty()) ? null : optCategory.get();
	}

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<Product> findProductByCategory(String categoryId) {
		// TODO Auto-generated method stub
		List<Product> products = productRepository.findAll();
		return products.stream().filter(o -> o.getCategory() != null)
				.filter(o -> o.getCategory().getName().equals(categoryId)).collect(Collectors.toList());
	}

}
