package com.exam.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.model.Role;
import com.exam.repo.RoleRepository;
import com.exam.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role save(Role role, Long id) {
		if(id == null) {
			role = roleRepository.save(role); 
		} else {
			Optional<Role> roleDB = roleRepository.findById(id);
			if(roleDB.isPresent()) {
				role.setId(id);
				role = roleRepository.save(role);
			} else {
				role = new Role();
			}
		}
		return role;
	}

	@Override
	public Role findById(Long id) {
		Optional<Role> roleOptional = roleRepository.findById(id);
		return roleOptional.isPresent() ? roleOptional.get() : new Role();
	}

	@Override
	public void deleteById(Long id) {
		roleRepository.deleteById(id);
	}

	@Override
	public List<Role> getAll() {
		return roleRepository.findAll();
	}

}
