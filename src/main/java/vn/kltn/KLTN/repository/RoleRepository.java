package vn.kltn.KLTN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.kltn.KLTN.entity.Role;
import vn.kltn.KLTN.enums.RoleAvailable;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Integer> {

	public Role findByType(RoleAvailable type);
}
