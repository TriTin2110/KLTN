package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.model.CartItemQuantityRequest;

public interface CartService {
	public Cart findByUserName(String userName);

	public Cart removeCartItemFromCart(Cart cart, String productName);

	public Cart addProductToCart(String userName, String productName, String productImage, String size, int price,
			int productQuantity, Cart userCart);

	public Cart updateAmount(String cartId, List<CartItemQuantityRequest> cartItemQuantityRequests);

	public Order ordering(User user);

	public boolean add(Cart cart);

	public Cart update(Cart cart);

	public boolean remove(String cartId);

	public Cart findById(String cartId);

	public List<Cart> findAll();

}
