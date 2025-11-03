package vn.kltn.KLTN.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.kltn.KLTN.entity.Chat;

@RepositoryRestResource
public interface ChatRepository extends JpaRepository<Chat, Integer> {
	@Modifying
	@Query("UPDATE Chat c set c.date=:time where c.id=:id")
	public void updateTime(@Param("id") int id, @Param("time") LocalDateTime localDateTime);

}
