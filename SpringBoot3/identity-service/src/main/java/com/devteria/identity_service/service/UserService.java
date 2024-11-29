package com.devteria.identity_service.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserUpdateRequest;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	// Create
	public User createRequest(UserCreationRequest request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.USER_EXISTED);
		}
		User user = modelMapper.map(request, User.class);
		return userRepository.save(user);
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
		request.setId(userId);
		request.setUsername(user.getUsername());
		user = modelMapper.map(request, User.class);
		return userRepository.save(user);
	}
	
	// Delete
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}
}
