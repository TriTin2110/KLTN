package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Ingredient;
import vn.kltn.KLTN.entity.Supplier;
import vn.kltn.KLTN.repository.IngredientRepository;
import vn.kltn.KLTN.service.IngredientService;
import vn.kltn.KLTN.service.SupplierService;

@Service
public class IngredientServiceImpl implements IngredientService {
	@Autowired
	private IngredientRepository repository;
	@Autowired
	private SupplierService supplierService;

	@Override
	@Transactional
	public boolean add(Ingredient ingredient) {
		// TODO Auto-generated method stub
		try {
			Supplier supplier = ingredient.getSupplier();
			if (supplier == null || supplierService.findById(supplier.getName()) != null) {
				repository.save(ingredient);
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
			Ingredient ingredient = findById(name);
			if (ingredient != null) {
				repository.delete(ingredient);
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
	public Ingredient update(Ingredient ingredient) {
		// TODO Auto-generated method stub
		try {
			repository.saveAndFlush(ingredient);
			return ingredient;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Ingredient findById(String ingredientId) {
		// TODO Auto-generated method stub
		Optional<Ingredient> opt = repository.findById(ingredientId);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	public List<Ingredient> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
