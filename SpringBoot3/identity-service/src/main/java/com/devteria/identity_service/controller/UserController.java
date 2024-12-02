package com.devteria.identity_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devteria.identity_service.dto.request.ApiResponse;
import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserUpdateRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
		try {
			UserResponse result = userService.createRequest(request);
			return ApiResponse.<UserResponse>builder()
			        .code(201)
			        .message(null)
			        .result(result)
			        .build();
        } catch (Exception ex) {
        	log.error("Transaction failed: " + ex.getMessage());
            throw ex;
        }
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<List<UserResponse>> getUsers() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("Username: " + authentication.getName());
		authentication.getAuthorities().forEach(grantedAuthority -> log.info("GrantedAuthority: " + grantedAuthority));

		return ApiResponse.<List<UserResponse>>builder()
		        .code(200)
		        .message(null)
		        .result(userService.getUsers())
		        .build();
	}
	
	@GetMapping("/{userId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Có thể đặt ở cả Controller và Service
	public ApiResponse<User> getUser(@PathVariable("userId") String userId) {
		return ApiResponse.<User>builder()
		        .code(200)
		        .message(null)
		        .result(userService.getUser(userId))
		        .build();
	}
	
	@PutMapping("/{userId}")
	@Transactional(rollbackFor = Exception.class)
	public ApiResponse<User> updateUser(@PathVariable("userId") String userId, @RequestBody @Valid UserUpdateRequest request) {
		try {
			return ApiResponse.<User>builder()
			        .code(200)
			        .message(null)
			        .result(userService.updateUser(userId, request))
			        .build();
        } catch (Exception ex) {
        	log.error("Transaction failed: " + ex.getMessage());
            throw ex;
        }
	}
	
	@DeleteMapping("/{userId}")
	@Transactional(rollbackFor = Exception.class)
	public ApiResponse<String> deleteUser(@PathVariable("userId") String userId) {
		try {
			userService.deleteUser(userId);
			return ApiResponse.<String>builder()
			        .code(200)
			        .message(null)
			        .result("Delete success")
			        .build();
        } catch (Exception ex) {
        	log.error("Transaction failed: " + ex.getMessage());
            throw ex;
        }
	}
	
	@GetMapping("/myInfo")
	public ApiResponse<UserResponse> getMyInfo() {
		return ApiResponse.<UserResponse>builder()
		        .code(200)
		        .message(null)
		        .result(userService.getMyInfo())
		        .build();
	}
}
