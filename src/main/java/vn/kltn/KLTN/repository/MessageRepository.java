package vn.kltn.KLTN.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.kltn.KLTN.entity.Message;

@RepositoryRestResource
public interface MessageRepository extends JpaRepository<Message, Integer> {
	public List<Message> findByChat_Id(int id); // Tìm danh sách message dựa theo thuộc tính id của Chat
}
