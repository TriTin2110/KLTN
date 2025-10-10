package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Ingredient;
import vn.kltn.KLTN.entity.Supplier;

public interface SupplierService {
	public Supplier add(Supplier supplier);

	public boolean remove(String name);

	public boolean removeAll();

	public Supplier update(Supplier supplier);

	public Supplier findById(String supplierId);

	public List<Supplier> findAll();

	public Supplier addIngredientsToList(Supplier supplier, Ingredient ingredient);
}
