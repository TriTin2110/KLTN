package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.Point;
import vn.kltn.KLTN.entity.User;

public interface PointService {
	public boolean add(User user, Order order);

	public boolean update(User user, Order order);

	public boolean remove(String pointId);

	public Point findById(String pointId);

	public List<Point> findAll();
}
