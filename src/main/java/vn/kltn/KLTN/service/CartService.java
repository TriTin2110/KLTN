package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Cart;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.User;

public interface CartService {
	public Cart findByUserName(String userName);

	public boolean removeProductFromCart(String userName, String productName);

	public Cart addProductToCart(String userName, String productName, int amount);

	public Cart updateAmount(String userName, String productName, int amount);

	public Order ordering(User user);

	public boolean add(Cart cart);

	public Cart update(Cart cart);

	public boolean remove(String cartId);

	public Cart findById(String cartId);

	public List<Cart> findAll();
}
