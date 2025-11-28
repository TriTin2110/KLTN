package vn.kltn.KLTN.model;

import vn.kltn.KLTN.entity.Cart;

public class UserDTO {
	private String username;
	private String email;
	private String address, fullName, phoneNumber, couponId;
	private Cart cart;

	public UserDTO() {
	}

	public UserDTO(String username, String email, String address, String fullName, String phoneNumber, Cart cart) {
		super();
		this.username = username;
		this.email = email;
		this.address = address;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.cart = cart;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", email=" + email + ", address=" + address + ", fullName=" + fullName
				+ ", phoneNumber=" + phoneNumber + "]";
	}

}
