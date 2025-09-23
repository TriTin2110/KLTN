package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Supplier;

public interface SupplierService {
	public boolean add(Supplier supplier);

	public boolean remove(String name);

	public Supplier update(Supplier supplier);

	public Supplier findById(String supplierId);

	public List<Supplier> findAll();
}
