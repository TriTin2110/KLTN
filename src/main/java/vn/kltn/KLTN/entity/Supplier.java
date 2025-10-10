package vn.kltn.KLTN.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Supplier {
	@Id
	private String name;
	private String address;
	private String phoneNumber;
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Ingredient> ingredients;

	public Supplier() {
	}

	public Supplier(String name, String address, String phoneNumber) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.ingredients = new ArrayList<Ingredient>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
		ingredient.setSupplier(this);
	}

	public boolean alreadyContainIngredient(String input) {
		for (Ingredient ingredient : ingredients) {
			if (ingredient.getName().equals(input))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Supplier [name=" + name + ", address=" + address + ", phoneNumber=" + phoneNumber + "]";
	}

}
