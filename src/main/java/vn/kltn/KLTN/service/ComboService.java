package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Combo;
import vn.kltn.KLTN.entity.Product;

public interface ComboService {
	public Combo saveAndUpdate(Product product1, Product product2, String shortDescription, short discount);

	public boolean remove(String comboId);

	public Combo findById(String comboId);

	public List<Combo> findAll();
}
