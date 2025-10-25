package vn.kltn.KLTN.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Product {
	@Id
	private String name;
	private String image;

	@Transient
	private int lowestPrice;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "size_price", joinColumns = @JoinColumn(name = "product_name"))
	@MapKeyColumn(name = "size")
	@Column(name = "price")
	private Map<String, Integer> sizePrice;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "category_id")
	private Category category;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "product_ingredient", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
	private List<Ingredient> ingredients;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "event_id")
	private Event event;
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;
	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Comment> comments;
	@ManyToMany
	@JoinTable(name = "product_combo", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "combo_id"))
	private List<Combo> combos;

	public Product() {
	}

	public Product(String name, String image) {
		this.name = name;
		this.image = image;
		this.sizePrice = new HashMap<String, Integer>();
		this.ingredients = new ArrayList<Ingredient>();
		this.comments = new ArrayList<Comment>();
		this.combos = new ArrayList<Combo>();
	}

	public Map<String, Integer> getSizePrice() {
		return sizePrice;
	}

	public void setSizePrice(Map<String, Integer> priceSize) {
		this.sizePrice = priceSize;
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

	public int getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(int lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}

	public void addCoupon(Coupon coupon) {
		this.coupon = coupon;
		coupon.setProduct(this);
	}

	public boolean alreadyContainIngredient(String input) {
		for (Ingredient ingredient : ingredients) {
			if (ingredient.getName().equals(input))
				return true;
		}
		return false;
	}

	public void sortSizePrice() {
		Map<String, Integer> map = this.getSizePrice().entrySet().stream()
				.sorted((o1, o2) -> o1.getValue().compareTo(o2.getValue())) // Sắp
				// xếp
				// theo
				// giá
				// trị
				// tăng
				// dần
				// của Value
				.collect(Collectors.toMap(Map.Entry::getKey, // Giữ key
						Map.Entry::getValue, // Giữ value
						(oldValue, newValue) -> oldValue, // xử lý key trùng
						LinkedHashMap::new)); // giữ thứ tự sắp xếp
		this.setSizePrice(map);
	}
}
