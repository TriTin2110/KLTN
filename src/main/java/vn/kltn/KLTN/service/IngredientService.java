package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Ingredient;

public interface IngredientService {
	public boolean add(Ingredient ingredient);

	public boolean remove(String name);

	public Ingredient update(Ingredient ingredient);

	public Ingredient findById(String ingredientId);

	public List<Ingredient> findAll();
}
