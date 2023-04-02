package com.example.movie.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.example.movie.dto.UserDTO;

public interface UserService {
	UserDTO findByUsernameAndPassword(String username, String password);
	boolean checkLogin(String username, String password);
	UserDTO findByUsername(String username);
	List<GrantedAuthority> getAuthorities(String username);
}
