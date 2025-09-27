package vn.kltn.KLTN.service;

import vn.kltn.KLTN.entity.Role;
import vn.kltn.KLTN.enums.RoleAvailable;

public interface RoleService {
	public Role add(Role role);

	public boolean remove(RoleAvailable type);

	public Role findByType(RoleAvailable type);

}
