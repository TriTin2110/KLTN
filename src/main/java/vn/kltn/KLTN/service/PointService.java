package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.Point;

public interface PointService {
	public boolean add(Point point);

	public boolean update(Point point);

	public boolean remove(String pointId);

	public Point findById(String pointId);

	public List<Point> findAll();

	public Point addOrder(String pointId, Order order);

	public List<Order> getAllOrder(String pointId);
}
