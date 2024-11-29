package com.devteria.identity_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserUpdateRequest;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	// Create
	public User createRequest(UserCreationRequest request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("User existed.");
		}
		User user = User.builder()
						.username(request.getUsername())
						.password(request.getPassword())
						.firstName(request.getFirstName())
						.lastName(request.getLastName())
						.dob(request.getDob())
						.build();
		User result = userRepository.save(user);
		return result;
	}
	
	// Get All
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	// Get One
	public User getUser(String id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
	}
	
	// Update
	public User updateUser(String userId, UserUpdateRequest request) {
		User user = getUser(userId);
		user.setPassword(request.getPassword());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setDob(request.getDob());
		return userRepository.save(user);
	}
	
	// Delete
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}
}