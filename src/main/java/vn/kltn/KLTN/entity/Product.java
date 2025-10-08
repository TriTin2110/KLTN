package vn.kltn.KLTN.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Product {
	@Id
	private String name;
	private String image;
	private List<Integer> prices;
	private List<String> sizes;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "category_id")
	private Category category;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "product_ingredient", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
	private List<Ingredient> ingredients;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "event_id")
	private Event event;
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_detail_id")
	private ProductDetail productDetail;
	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	private List<Comment> comments;
	@ManyToMany
	@JoinTable(name = "product_combo", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "combo_id"))
	private List<Combo> combos;

	public Product() {
	}

	public Product(String name, String image) {
		this.name = name;
		this.image = image;
		this.prices = new ArrayList<Integer>();
		this.sizes = new ArrayList<String>();
		this.ingredients = new ArrayList<Ingredient>();
		this.comments = new ArrayList<Comment>();
		this.combos = new ArrayList<Combo>();
	}

	public List<Integer> getPrices() {
		return prices;
	}

	public void setPrices(List<Integer> prices) {
		this.prices = prices;
	}

	public List<String> getSizes() {
		return sizes;
	}

	public void setSizes(List<String> sizes) {
		this.sizes = sizes;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public ProductDetail getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Combo> getCombos() {
		return combos;
	}

	public void setCombos(List<Combo> combos) {
		this.combos = combos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
		ingredient.getProducts().add(this);
	}

	public void addCoupon(Coupon coupon) {
		this.coupon = coupon;
		coupon.setProduct(this);
	}

	@Override
	public String toString() {
		List<Ingredient> ingredients = this.ingredients;
		List<Integer> prices = this.getPrices();
		List<String> sizes = this.getSizes();
		String ingredientString = ingredients.stream().map(o -> o.getName()).collect(Collectors.joining("|"));
		String pricesString = prices.stream().map(o -> String.valueOf(o)).collect(Collectors.joining("|"));
		String sizeString = sizes.stream().map(o -> o).collect(Collectors.joining("|"));
		return "Product [name=" + getName() + ", price=" + pricesString + ", size=" + sizeString + ", category="
				+ category + ", ingredients=" + ingredientString + ", event=" + event + ", coupon=" + coupon
				+ ", productDetail=" + productDetail + "]";
	}

}
