package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Optional;

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
			if (findById(supplier.getName()) == null) {
				repository.save(supplier);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public boolean remove(String name) {
		// TODO Auto-generated method stub
		try {
			Supplier supplier = findById(name);
			if (supplier != null) {
				repository.delete(supplier);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Supplier update(Supplier supplier) {
		// TODO Auto-generated method stub
		try {
			if (findById(supplier.getName()) != null)
				return repository.saveAndFlush(supplier);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Supplier findById(String supplierId) {
		// TODO Auto-generated method stub
		Optional<Supplier> opt = repository.findById(supplierId);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	public List<Supplier> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
