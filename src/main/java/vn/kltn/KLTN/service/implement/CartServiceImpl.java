package vn.kltn.KLTN.service.implement;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.enums.OrderStatus;
import vn.kltn.KLTN.repository.CartRepository;
import vn.kltn.KLTN.service.CartService;
import vn.kltn.KLTN.service.OrderService;
import vn.kltn.KLTN.service.ProductService;
import vn.kltn.KLTN.service.UserService;

@Service
public class CartServiceImpl implements CartService {
	private CartRepository repository;
	private ProductService productService;
	private UserService userService;
	@Autowired
	private OrderService orderService;

	@Autowired
	public CartServiceImpl(CartRepository repository, @Lazy UserService userService, ProductService productService) {
		this.repository = repository;
		this.userService = userService;
		this.productService = productService;
	}

	@Override
	public Cart findByUserName(String userName) {
		// TODO Auto-generated method stub
		User user = userService.findById(userName);
		return repository.findByUser(user);
	}

	@Override
	@Transactional
	public boolean removeProductFromCart(String userName, String productName) {
		// TODO Auto-generated method stub
		try {
			Cart cart = findByUserName(userName);
			if (cart == null)
				return false;

			Map<Product, Integer> cartItems = cart.getCartItems();
			if (cartItems == null || cartItems.isEmpty())
				return false;

			Product product = productService.findById(productName);
			if (product == null)
				return false;
			cartItems.remove(product);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Cart addProductToCart(String userName, String productName, int amount) {
		// TODO Auto-generated method stub
		Cart cart = findByUserName(userName);
		if (cart == null || amount <= 0)
			return null;
		try {
			Map<Product, Integer> cartItems = cart.getCartItems();
			Product product = productService.findById(productName);
			if (product == null)
				return null;
			cartItems.put(product, amount);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return cart;
	}

	@Override
	@Transactional
	public Cart updateAmount(String userName, String productName, int amount) {
		// TODO Auto-generated method stub
		Optional<Cart> opt = repository.findById(userName);
		if (opt.isEmpty())
			return null;
		Cart cart = opt.get();
		Map<Product, Integer> cartItems = cart.getCartItems();
		Product product = productService.findById(productName);
		if (product == null)
			return null;
		if (cartItems.put(product, amount) != null) {// if result == null => that product not exists in
			// cartItems
			cart.setCartItems(cartItems);
			return repository.saveAndFlush(cart);
		}
		return null;
	}

	@Override
	@Transactional
	public Order ordering(User user) {
		// TODO Auto-generated method stub
		String id = user.getUserName() + "-" + System.currentTimeMillis();
		Date date = new Date(System.currentTimeMillis());
		int totalPrice = 0;
		Optional<Cart> opt = repository.findById(user.getUserName());
		if (opt.isEmpty())
			return null;
		Cart cart = opt.get();
		Map<Product, Integer> orderItems = cart.getCartItems();
		totalPrice = orderItems.keySet().stream().mapToInt(o -> o.getPrice() * orderItems.get(o)).sum();
		Order order = new Order(id, date, totalPrice, OrderStatus.PENDING, cart, user, orderItems);
		if (orderService.add(order)) {
			orderItems.clear();
			cart.setCartItems(orderItems);
			repository.save(cart);
			return order;
		}
		return null;
	}

	@Override
	@Transactional
	public boolean add(Cart cart) {
		// TODO Auto-generated method stub
		try {
			User user = cart.getUser();
			user.setCart(cart);
			if (repository.save(cart) != null) {
				userService.update(user);
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
	public Cart update(Cart cart) {
		// TODO Auto-generated method stub
		Cart cartInDB = findById(cart.getId());
		if (cartInDB == null)
			return null;
		return repository.saveAndFlush(cart);
	}

	@Override
	@Transactional
	public boolean remove(String cartId) {
		// TODO Auto-generated method stub
		try {
			Cart cart = findById(cartId);
			if (cart != null) {
				repository.delete(cart);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Cart findById(String cartId) {
		// TODO Auto-generated method stub
		Optional<Cart> optCart = repository.findById(cartId);
		return (optCart.isEmpty()) ? null : optCart.get();
	}

	@Override
	public List<Cart> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
