package vn.kltn.KLTN.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.entity.Point;
import vn.kltn.KLTN.enums.OrderStatus;
import vn.kltn.KLTN.enums.Rank;
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

	@Override
	@Transactional
	// @Async
	public void updatePointCompletedOrder(Point point, Order order) {
	    try {
	        if (point == null || order == null) {
	            return;
	        }

	        // tích điểm khi đơn hàng COMPLETED
	        if (order.getStatus() == null || order.getStatus() != OrderStatus.COMPLETED) {
	            return;
	        }

	        //  Lấy số tiền thanh toán và cộng vào totalSpent
	        int orderTotalPrice = order.getTotalPrice();
	        if (orderTotalPrice <= 0) {
	            return;
	        }

	        int currentTotalSpent = point.getTotalSpent();
	        point.setTotalSpent(currentTotalSpent + orderTotalPrice);

	        // B4: Lấy hạng hiện tại của user
	        Rank currentRank = point.getUserRank(); // Rank: SILVER, GOLD, ...

	        // B5: Tính điểm dựa trên hạng
	        // SILVER: 10.000đ = 1 điểm
	        // GOLD  : 10.000đ = 3 điểm
	        int rate;
	        if (currentRank == Rank.GOLD) {
	            rate = 3;
	        } else {
	            // Chưa có hạng hoặc SILVER -> mặc định tích điểm như Bạc
	            rate = 1;
	        }

	        int pointsToAdd = (orderTotalPrice / 10000) * rate;

	        // Cộng điểm tích lũy
	        int currentAccumulatedPoint = point.getAccumulatedPoint();
	        int newAccumulatedPoint = currentAccumulatedPoint + pointsToAdd;
	        point.setAccumulatedPoint(newAccumulatedPoint);

	        // Kiểm tra và thăng hạng theo accumulatedPoint
	        // 1 den 1000  -> SILVER
	        // 1001+     -> GOLD
	        if (newAccumulatedPoint >= 1001) {
	            point.setUserRank(Rank.GOLD);
	        } else if (newAccumulatedPoint >= 1) {
	            point.setUserRank(Rank.SILVER);
	        } else {
	            // Chưa có điểm nào -> chưa có hạng
	            point.setUserRank(null);
	        }

	        //  Lưu 
	        repository.saveAndFlush(point);
//	        
//	        // test console ne`
//	        System.out.println("test cập nhật điểm cho user: " 
//	        	    
//	        	    + " | Hạng hiện tại: " + point.getUserRank()
//	        	    + " | Tổng chi tiêu: " + point.getTotalSpent()
//	        	    + " | Điểm tích lũy: " + point.getAccumulatedPoint());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


}
