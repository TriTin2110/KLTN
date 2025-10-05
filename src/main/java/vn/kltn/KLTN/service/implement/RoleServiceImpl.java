package vn.kltn.KLTN.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Role;
import vn.kltn.KLTN.enums.RoleAvailable;
import vn.kltn.KLTN.repository.RoleRepository;
import vn.kltn.KLTN.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository repository;

	@Override
	@Transactional
	public Role add(Role role) {
		// TODO Auto-generated method stub
		if (findByType(role.getType()) == null)
			return repository.save(role);
		return null;
	}

	@Override
	@Transactional
	public boolean remove(RoleAvailable type) {
		// TODO Auto-generated method stub
		try {
			Role role = findByType(type);
			if (role != null) {
				repository.delete(role);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Role findByType(RoleAvailable type) {
		// TODO Auto-generated method stub
		Role role = repository.findByType(type);
		if (role == null) {
			role = new Role(type);
			role = repository.save(role);
		}
		return role;
	}

}
