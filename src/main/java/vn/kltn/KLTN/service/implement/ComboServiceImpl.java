package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Combo;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.repository.ComboRepository;
import vn.kltn.KLTN.service.ComboService;

@Service
public class ComboServiceImpl implements ComboService {
	@Autowired
	private ComboRepository repository;

	@Override
	@Transactional
	public Combo saveAndUpdate(Product product1, Product product2, String shortDescription, short discount) {
		// TODO Auto-generated method stub
		String product1Name = product1.getName();
		String product2Name = product2.getName();

		if (!product1.getName().equals(product2.getName())) {
			try {
				int totalPrice = (int) Math
						.ceil(((1 - ((float) discount / 100)) * (product1.getPrice() + product2.getPrice())));
				Combo combo = new Combo(product1Name + "-" + product2Name, shortDescription, totalPrice, discount,
						List.of(product1, product2));
				return repository.saveAndFlush(combo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	@Transactional
	public boolean remove(String comboId) {
		// TODO Auto-generated method stub
		try {
			Combo combo = findById(comboId);
			if (combo != null) {
				repository.delete(combo);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Combo findById(String comboId) {
		// TODO Auto-generated method stub
		Optional<Combo> opt = repository.findById(comboId);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	public List<Combo> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
