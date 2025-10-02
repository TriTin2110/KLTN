package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Category;
import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.entity.Coupon;
import vn.kltn.KLTN.entity.Event;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.repository.ProductRepository;
import vn.kltn.KLTN.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository repository;

	@Override
	@Transactional
	public boolean add(Product product) {
		// TODO Auto-generated method stub
		try {
			if (product.getIngredients().isEmpty() || product.getProductDetail() == null
					|| product.getCategory() == null || product.getPrice() == 0)
				return false;

			repository.save(product);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public boolean remove(String productName) {
		// TODO Auto-generated method stub
		try {
			Product product = findById(productName);
			if (product != null) {
				setTotalProductOfCategory(product, product.getCategory().getTotalProduct() - 1);
				repository.delete(product);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean update(Product product) {
		// TODO Auto-generated method stub
		try {
			repository.saveAndFlush(product);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Product findById(String productName) {
		// TODO Auto-generated method stub
		Optional<Product> opt = repository.findById(productName);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	private void setTotalProductOfCategory(Product product, int totalProduct) {
		Category category = product.getCategory();
		category.setTotalProduct(totalProduct);
	}

	@Override
	@Transactional
	public boolean updateComment(Product product, Comment comment) {
		// TODO Auto-generated method stub
		try {
			product = findById(product.getName());
			List<Comment> comments = product.getComments();
			comments.add(comment);
			product.setComments(comments);
			repository.saveAndFlush(product);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateEvent(Product product, Event event) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		try {
			product = findById(product.getName());
			product.setEvent(event);
			repository.saveAndFlush(product);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public boolean updateCoupon(Product product, Coupon coupon) {
		// TODO Auto-generated method stub
		try {
			product.setCoupon(coupon);
			repository.saveAndFlush(product);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
