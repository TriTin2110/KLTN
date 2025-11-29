package vn.kltn.KLTN.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.enums.OrderStatus;
import vn.kltn.KLTN.model.OrderDetailProfileDTO;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, String> {

	@Query("select o from Order o where o.id like :username%")
	public List<Order> findByUsername(@Param("username") String username);

	// Không lấy những đơn hàng bị hủy hay hoàn thành
	@Query("SELECT o FROM Order o LEFT JOIN o.orderItem WHERE o.totalPrice > 0 and o.status != 'COMPLETED' and o.status !='CANCELLED' ")
	public List<Order> findAllWithItems();

	@Query("UPDATE Order o SET o.status=:status WHERE o.id=:id and o.status!='CANCELLED'")
	@Modifying
	public int updateStatus(@Param("id") String orderId, @Param("status") OrderStatus status);

	@Query("SELECT new vn.kltn.KLTN.model.OrderDetailProfileDTO(o.id, o.createdDate, o.totalPrice, o.status) FROM Order o where o.point.id=:id")
	public List<OrderDetailProfileDTO> findByPointId(@Param("id") String id);
	
	// ================== THỐNG KÊ DOANH THU / SỐ ĐƠN ==================
	@Query("SELECT o.createdDate, SUM(o.totalPrice) FROM Order o " +
	       "WHERE o.status = vn.kltn.KLTN.enums.OrderStatus.COMPLETED " +
	       "GROUP BY o.createdDate ORDER BY o.createdDate ASC")
	List<Object[]> getRevenueByDay();

	@Query("SELECT o.createdDate, COUNT(o.id) FROM Order o " +
	       "WHERE o.status = vn.kltn.KLTN.enums.OrderStatus.COMPLETED " +
	       "GROUP BY o.createdDate ORDER BY o.createdDate ASC")
	List<Object[]> getOrderCountByDay();

	@Query("SELECT FUNCTION('MONTH', o.createdDate), SUM(o.totalPrice) FROM Order o " +
	       "WHERE o.status = vn.kltn.KLTN.enums.OrderStatus.COMPLETED " +
	       "GROUP BY FUNCTION('MONTH', o.createdDate) ORDER BY FUNCTION('MONTH', o.createdDate)")
	List<Object[]> getRevenueByMonth();
	
	@Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.status='COMPLETED' AND o.createdDate = :date")
	Integer sumRevenueByDate(@Param("date") Date date);

	@Query("SELECT COUNT(o.id) FROM Order o WHERE o.status='COMPLETED' AND o.createdDate = :date")
	Integer countOrdersByDate(@Param("date") Date date);

	@Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.status='COMPLETED' AND FUNCTION('MONTH', o.createdDate) = :month")
	Integer sumRevenueByMonth(@Param("month") int month);

	@Query("SELECT COUNT(o.id) FROM Order o WHERE o.status='COMPLETED' AND FUNCTION('MONTH', o.createdDate) = :month")
	Integer countOrdersByMonth(@Param("month") int month);
	
	@Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.status = 'COMPLETED'")
	Integer sumAllRevenue();

}
