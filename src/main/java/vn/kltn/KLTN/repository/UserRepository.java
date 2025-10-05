package vn.kltn.KLTN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.kltn.KLTN.entity.User;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, String> {
	public User findByEmail(String email);
}
