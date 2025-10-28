package vn.kltn.KLTN.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.repository.CommentRepository;
import vn.kltn.KLTN.service.CommentService;
import vn.kltn.KLTN.service.ProductService;
import vn.kltn.KLTN.service.UserService;

@Service
public class CommentServiceImpl implements CommentService {
	private CommentRepository repository;
	private ProductService productService;
	private UserService userService;

	@Autowired
	public CommentServiceImpl(CommentRepository repository, ProductService productService,
			@Lazy UserService userService) {
		this.repository = repository;
		this.productService = productService;
		this.userService = userService;
	}

	@Override
	@Transactional
	public Comment add(Comment comment, String productId, String userName) {
		// TODO Auto-generated method stub
		try {
			Product product = productService.findById(productId);
			User user = userService.findById(userName);
			if (product == null || user == null)
				return null;
//			product.getComments().add(comment);
			comment.setProduct(product);
//			user.getComments().add(comment);
			comment.setUser(user);
//			product.addComment(comment);
//			user.addComment(comment);
			repository.save(comment);
			return comment;
//			if (product != null && productService.findById(product.getName()) != null) {
//				User user = comment.getUser();
//				if (user != null && userService.findById(user.getUsername()) != null) {
//					if (findById(comment.getId()) == null) {
//						repository.save(comment);
//						productService.updateComment(product, comment);
//						userService.updateComment(user, comment);
//						return true;
//					}
//				}
//			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public boolean remove(String id) {
		// TODO Auto-generated method stub
		try {
			Comment comment = findById(id);
			if (comment != null) {
				repository.delete(comment);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public List<Comment> findAllByProductId(String productId) {
		// TODO Auto-generated method stub
		Product product = productService.findById(productId);
		if (product != null) {
			List<Comment> comments = new ArrayList<Comment>(product.getComments());
			return comments;
		}
		return null;
	}

	@Override
	public Comment findById(String commentId) {
		// TODO Auto-generated method stub
		Optional<Comment> opt = repository.findById(commentId);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	public List<Comment> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	@Transactional
	public boolean update(Comment comment) {
		// TODO Auto-generated method stub
		try {
			repository.saveAndFlush(comment);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

}
