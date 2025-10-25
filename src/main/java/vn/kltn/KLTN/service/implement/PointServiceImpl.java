package vn.kltn.KLTN.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.Point;
import vn.kltn.KLTN.repository.PointRepository;
import vn.kltn.KLTN.service.PointService;

@Service
public class PointServiceImpl implements PointService {
	@Autowired
	private PointRepository repository;

	@Override
	@Transactional
	public boolean add(Point point) {
		// TODO Auto-generated method stub
		try {
			if (findById(point.getId()) == null) {
				repository.save(point);
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
	public boolean update(Point point) {
		// TODO Auto-generated method stub
		try {
			if (findById(point.getId()) != null) {
				repository.saveAndFlush(point);
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
	public boolean remove(String pointId) {
		// TODO Auto-generated method stub
		try {
			Point point = findById(pointId);
			if (point != null) {
				repository.delete(point);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Point findById(String pointId) {
		// TODO Auto-generated method stub
		Optional<Point> opt = repository.findById(pointId);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	public List<Point> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	@Transactional
	public Point addOrder(String pointId, Order order) {
		// TODO Auto-generated method stub
		Point point = findById(pointId);
		point.getOrder().add(order);
		return point;
	}

	@Override
	@Transactional
	public List<Order> getAllOrder(String pointId) {
		// TODO Auto-generated method stub
		Point point = findById(pointId);
		List<Order> orders = new ArrayList<Order>(point.getOrder());
		return orders;
	}

}
