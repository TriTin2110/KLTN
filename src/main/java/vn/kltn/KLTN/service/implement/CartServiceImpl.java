package vn.kltn.KLTN.service.implement;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.CartItem;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.entity.User;
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
//		return repository.findByUser(user);
		return null;

	}

	@Override
	@Transactional
	public boolean removeProductFromCart(String userName, String productName) {
		// TODO Auto-generated method stub
		try {
			Cart cart = findByUserName(userName);
			if (cart == null)
				return false;

			List<CartItem> cartItems = cart.getCartItems();
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
	public Cart addProductToCart(String userName, String productName, String productImage, String size, int price,
			int productQuantity, Cart cart) {
		// TODO Auto-generated method stub
		if (cart == null) {
			cart = new Cart(userName);
		}

		List<CartItem> cartItems = cart.getCartItems();
		StringBuilder builder = new StringBuilder();
		builder.append(productName);
		builder.append("-");
		builder.append(userName);
		builder.append("-");
		builder.append(size);
		String id = Base64.getEncoder().encodeToString(builder.toString().getBytes());
		CartItem cartItem = cartItemAlreayExists(cartItems, id);
		if (cartItem == null) {
			cartItem = new CartItem(id, price, productQuantity, size, productName, productImage);
			cartItems.add(cartItem);
		} else {
			cartItem.setQuantity(cartItem.getQuantity() + productQuantity);
			int index = cartItems.indexOf(cartItem);
			cartItems.set(index, cartItem);
		}
		return cart;
	}

	private CartItem cartItemAlreayExists(List<CartItem> cartItems, String id) {
		Optional<CartItem> opt = cartItems.stream().filter(cartItem -> id.equals(cartItem.getItemId())).findFirst();
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	@Transactional
	public Cart updateAmount(String userName, String productName, CartItem cartItem) {
		// TODO Auto-generated method stub
		Optional<Cart> opt = repository.findById(userName);
		if (opt.isEmpty())
			return null;
		Cart cart = opt.get();
		List<CartItem> cartItems = cart.getCartItems();
		Optional<CartItem> optCartItem = cartItems.stream().filter(o -> o.getItemId() == cartItem.getItemId())
				.findFirst();
		if (optCartItem.isPresent()) {
			CartItem cartItemExisted = optCartItem.get();
			cartItemExisted.setData(cartItem);
			return repository.saveAndFlush(cart);
		}
		return null;
	}

	@Override
	@Transactional
	public Order ordering(User user) {
		// TODO Auto-generated method stub
//		String id = user.getUserName() + "-" + System.currentTimeMillis();
//		Date date = new Date(System.currentTimeMillis());
//		int totalPrice = 0;
//		Optional<Cart> opt = repository.findById(user.getUserName());
//		if (opt.isEmpty())
//			return null;
//		Cart cart = opt.get();
//		Map<Product, Integer> orderItems = cart.getCartItems();
//		totalPrice = orderItems.keySet().stream().mapToInt(o -> o.getPrice() * orderItems.get(o)).sum();
//		Order order = new Order(id, date, totalPrice, OrderStatus.PENDING, cart, user, orderItems);
//		if (orderService.add(order)) {
//			orderItems.clear();
//			cart.setCartItems(orderItems);
//			repository.save(cart);
//			return order;
//		}
		return null;
	}

	@Override
	@Transactional
	public boolean add(Cart cart) {
		// TODO Auto-generated method stub
		try {
//			User user = cart.getUser();
//			user.setCart(cart);
//			if (repository.save(cart) != null) {
//				userService.update(user);
//				return true;
//			}
			return true;
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
		cart.setTotalPrice(getTotalPrice(cart));
		return repository.saveAndFlush(cart);
	}

	private int getTotalPrice(Cart cart) {
		return cart.getCartItems().stream().mapToInt(cartItem -> cartItem.getPrice() * cartItem.getQuantity()).sum();
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
