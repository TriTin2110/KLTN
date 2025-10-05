package vn.kltn.KLTN.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.kltn.KLTN.entity.Order;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, String> {

	@Query("select o from Order o where o.id like :username%")
	public List<Order> findByUsername(@Param("username") String username);
}
