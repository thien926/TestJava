package com.devteria.identity_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserUpdateRequest;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	public User createUser(@RequestBody UserCreationRequest request) {
		try {
			User result = userService.createRequest(request);
			return result;
        } catch (Exception ex) {
        	log.error("Transaction failed: " + ex.getMessage());
            throw ex;
        }
	}
	
	@GetMapping
	public List<User> getUsers() {
		return userService.getUsers();
	}
	
	@GetMapping("/{userId}")
	public User getUser(@PathVariable("userId") String userId) {
		return userService.getUser(userId);
	}
	
	@PutMapping("/{userId}")
	@Transactional(rollbackFor = Exception.class)
	public User updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
		try {
			return userService.updateUser(userId, request);
        } catch (Exception ex) {
        	log.error("Transaction failed: " + ex.getMessage());
            throw ex;
        }
	}
	
	@DeleteMapping("/{userId}")
	@Transactional(rollbackFor = Exception.class)
	public String deleteUser(@PathVariable("userId") String userId) {
		try {
			userService.deleteUser(userId);
			return "Delete success";
        } catch (Exception ex) {
        	log.error("Transaction failed: " + ex.getMessage());
            throw ex;
        }
	}
}
