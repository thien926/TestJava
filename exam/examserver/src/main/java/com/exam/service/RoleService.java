package com.exam.service;

import java.util.List;

import com.exam.model.Role;

public interface RoleService {
	public Role save(Role role, Long id);
	public Role findById(Long id);
	public void deleteById(Long id);
	public List<Role> getAll();
}
