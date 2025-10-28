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
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.model.CartItemQuantityRequest;
import vn.kltn.KLTN.repository.CartItemRespository;
import vn.kltn.KLTN.repository.CartRepository;
import vn.kltn.KLTN.service.CartService;
import vn.kltn.KLTN.service.ProductService;
import vn.kltn.KLTN.service.UserService;

@Service
public class CartServiceImpl implements CartService {
	private CartRepository repository;
	private ProductService productService;
	private UserService userService;
	private CartItemRespository cartItemRespository;

	@Autowired
	public CartServiceImpl(CartRepository repository, @Lazy UserService userService, ProductService productService,
			CartItemRespository cartItemRespository) {
		this.repository = repository;
		this.userService = userService;
		this.productService = productService;
		this.cartItemRespository = cartItemRespository;
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
	public Cart removeCartItemFromCart(Cart cart, String productName) {
		// TODO Auto-generated method stub
		try {
			if (cart == null)
				return cart;

			List<CartItem> cartItems = cart.getCartItems();
			if (cartItems == null || cartItems.isEmpty())
				return null;

			CartItem cartItem = null;
			for (CartItem c : cartItems) {
				if (c.getProductId().equals(productName)) {
					cartItem = c;
					break;
				}
			}
			if (cartItem == null)
				return null;
			cartItems.remove(cartItem);
			cart.setTotalPrice(getTotalPrice(cart));
			return repository.saveAndFlush(cart);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
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
		builder.append("-");
		builder.append(System.currentTimeMillis());
		String id = Base64.getEncoder().encodeToString(builder.toString().getBytes());
		CartItem CartItem = cartItemAlreayExists(cartItems, productName, size); // Sản phẩm có id này đã tồn tại trong
																				// giỏ
		// hàng nào đó
		if (CartItem == null) {
			CartItem = new CartItem(id, price, productQuantity, size, productName, productImage);
			cartItems.add(CartItem);
		} else {
			CartItem.setQuantity(CartItem.getQuantity() + productQuantity);// Cập nhật số lượng nếu sản phẩm đã tồn tại
																			// trong giỏ
			int index = cartItems.indexOf(CartItem);
			cartItems.set(index, CartItem);
		}
		return cart;
	}

	private CartItem cartItemAlreayExists(List<CartItem> cartItems, String id, String size) {
		Optional<CartItem> opt = cartItems.stream()
				.filter(CartItem -> id.equals(CartItem.getProductId()) && size.equals(CartItem.getSize())).findFirst();
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	@Transactional
	public Cart updateAmount(String cartId, List<CartItemQuantityRequest> cartItemQuantityRequests) {
		// TODO Auto-generated method stub
		Optional<Cart> opt = repository.findById(cartId);
		if (opt.isEmpty())
			return null;
		Cart cart = opt.get();
		List<CartItem> cartItems = cart.getCartItems();
		for (CartItemQuantityRequest cartItemQuantityRequest : cartItemQuantityRequests) {
			for (CartItem cartItem : cartItems) {
				if (cartItem.getItemId().equals(cartItemQuantityRequest.getItemId())) {
					if (cartItem.getQuantity() != cartItemQuantityRequest.getQuantity()) {
						cartItem.setQuantity(cartItemQuantityRequest.getQuantity());
						cartItemRespository.saveAndFlush(cartItem);
					}
				}
			}
		}
		cart.setTotalPrice(getTotalPrice(cart));
		return cart;
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
		return cart.getCartItems().stream().mapToInt(CartItem -> CartItem.getPrice() * CartItem.getQuantity()).sum();
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
