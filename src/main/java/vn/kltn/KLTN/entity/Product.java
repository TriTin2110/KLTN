package vn.kltn.KLTN.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
public class Product extends Component {
	private int price;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "category_id")
	private Category category;
	@ManyToMany
	@JoinTable(name = "product_ingredient", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
	private List<Ingredient> ingredients;
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	@OneToOne
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_detail_id")
	private ProductDetail productDetail;
	@OneToMany(mappedBy = "product")
	private List<Comment> comments;
	@ManyToMany
	@JoinTable(name = "product_combo", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "combo_id"))
	private List<Combo> combos;

	public Product() {
	}

	public Product(String name, String image, int price) {
		super(name, image);
		this.price = price;
	}

	public Product(String name, String image, int price, Category category, List<Ingredient> ingredients) {
		super(name, image);
		this.price = price;
		this.category = category;
		this.ingredients = ingredients;
		this.productDetail = productDetail;
		this.comments = new ArrayList<Comment>();
		this.combos = new ArrayList<Combo>();
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	@Override
	public String toString() {
		return "Product [price=" + price + ", category=" + category + ", event=" + event + ", coupon=" + coupon
				+ ", productDetail=" + productDetail + ", getName()=" + getName() + "]";
	}

}
