package vn.kltn.KLTN.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.kltn.KLTN.entity.Notification;

@RepositoryRestResource
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	public List<Notification> findByUserIdOrderByLocalDateTimeDesc(String userId);

	public Notification findByContent(String content);

	@Modifying
	@Query("UPDATE Notification n SET n.type=:status, n.localDateTime=:date WHERE n.content=:orderId")
	public void updateStatus(@Param("orderId") String orderId, @Param("status") String status,
			@Param("date") LocalDateTime localDateTime);
}
