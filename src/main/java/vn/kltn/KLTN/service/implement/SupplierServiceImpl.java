package vn.kltn.KLTN.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Supplier;
import vn.kltn.KLTN.repository.SupplierRepository;
import vn.kltn.KLTN.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {
	@Autowired
	private SupplierRepository repository;

	@Override
	@Transactional
	public boolean add(Supplier supplier) {
		// TODO Auto-generated method stub
		try {
			repository.save(supplier);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean remove(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Supplier update(Supplier supplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supplier findById(String supplierId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Supplier> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
