package com.exam.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User save(User user, Long id, Long roleId) {
		if(id == null) {
			Set<UserRole> userRoles = new HashSet<>();
			Role role = roleRepository.findById(roleId).get();
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUser(user);
			userRoles.add(userRole);
			
			user.setUserRoles(userRoles);
			user = userRepository.save(user); 
		} else {
			Optional<User> roleDB = userRepository.findById(id);
			if(roleDB.isPresent()) {
				user.setId(id);
				user = userRepository.save(user);
			} else {
				user = new User();
			}
		}
		return user;
	}

	@Override
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user != null ? user : new User();
	}

	@Override
	public void deleteById(Long userId) {
		this.userRepository.deleteById(userId);;
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

}
