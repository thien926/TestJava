package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserUpdateRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.entity.Role;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.repository.RoleRepository;
import com.devteria.identity_service.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    // Create
    public UserResponse createRequest(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        List<Role> roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(roles);
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }
    
    public UserResponse getMyInfo() {
    	SecurityContext context = SecurityContextHolder.getContext();
    	String username = context.getAuthentication().getName();
    	
    	Jwt jwt = (Jwt) context.getAuthentication().getPrincipal();
        String userId = jwt.getClaimAsString("userId");
        log.info("getMyInfo : " + userId);
    	
    	return modelMapper.map(userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Found")), UserResponse.class);
    }

    // Get All
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(item -> modelMapper.map(item, UserResponse.class)).collect(Collectors.toList());
    }

    // Get One
    // Nếu 'userId' trong JWT claims bằng 'id' thì người dùng có quyền truy cập phương thức này.
//    @PreAuthorize("principal.claims['userId'] == #id") 
    // Kiểm tra xem tên người dùng trong đối tượng trả về có trùng với tên người dùng hiện tại (authentication.name) hay không.
    @PostAuthorize("returnObject.username == authentication.name")
    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    // Update
    public User updateUser(String userId, UserUpdateRequest request) {
        User user = getUser(userId);
        request.setId(userId);
        request.setUsername(user.getUsername());
        user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        List<Role> roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    // Delete
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
