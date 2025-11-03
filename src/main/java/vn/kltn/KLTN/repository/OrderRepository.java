package vn.kltn.KLTN.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.kltn.KLTN.entity.Order;
import vn.kltn.KLTN.enums.OrderStatus;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, String> {

	@Query("select o from Order o where o.id like :username%")
	public List<Order> findByUsername(@Param("username") String username);

	@Query("SELECT o FROM Order o LEFT JOIN o.orderItem WHERE o.totalPrice > 0 and o.status != 'COMPLETED' and o.status !='COMPLETED' ")
	public List<Order> findAllWithItems();

	@Query("UPDATE Order o SET o.status=:status WHERE o.id=:id")
	@Modifying
	public int updateStatus(@Param("id") String orderId, @Param("status") OrderStatus status);
}
