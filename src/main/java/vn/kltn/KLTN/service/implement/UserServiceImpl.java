package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.repository.UserRepository;
import vn.kltn.KLTN.service.CartService;
import vn.kltn.KLTN.service.CommentService;
import vn.kltn.KLTN.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;
	@Autowired
	private CartService cartService;
	@Autowired
	private CommentService commentService;

	@Override
	public User signIn(String userName, String password) {
		// TODO Auto-generated method stub
		User user = findById(userName);
		if (user != null)
			if (password.equals(user.getPassword()))
				return user;
		return null;
	}

	@Override
	@Transactional
	public User signUp(User user) {
		// TODO Auto-generated method stub
		try {
			if (findById(user.getUserName()) == null) {
				Cart cart = new Cart(user.getUserName(), user, null);
				user.setCart(cart);
				return repository.save(user);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public User update(User user) {
		// TODO Auto-generated method stub
		try {
			if (findById(user.getUserName()) != null)
				return repository.saveAndFlush(user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User findById(String userName) {
		// TODO Auto-generated method stub
		Optional<User> optUser = repository.findById(userName);
		return (optUser.isEmpty()) ? null : optUser.get();
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	@Transactional
	public boolean delete(String userName) {
		// TODO Auto-generated method stub
		try {
			User user = findById(userName);
			if (user != null) {
				if (cartService.remove(userName)) {
					user.getComments().forEach(o -> {
						o.setUser(null);
						commentService.update(o);
					});
					repository.delete(user);
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public boolean updateComment(User user, Comment comment) {
		// TODO Auto-generated method stub
		try {
			user = findById(user.getUserName());
			List<Comment> comments = user.getComments();
			comments.add(comment);
			user.setComments(comments);
			repository.saveAndFlush(user);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

}
