package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Ingredient;
import vn.kltn.KLTN.repository.IngredientRepository;
import vn.kltn.KLTN.service.IngredientService;

@Service
public class IngredientServiceImpl implements IngredientService {
	@Autowired
	private IngredientRepository repository;

	@Override
	@Transactional
	public boolean add(Ingredient ingredient) {
		// TODO Auto-generated method stub
		try {
			repository.save(ingredient);
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
	public Ingredient checkingStatus(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ingredient update(Ingredient ingredient) {
		// TODO Auto-generated method stub
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
