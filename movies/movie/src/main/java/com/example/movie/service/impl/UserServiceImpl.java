package com.example.movie.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.movie.dto.UserDTO;
import com.example.movie.entity.User;
import com.example.movie.repo.UserRepository;
import com.example.movie.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDTO findByUsernameAndPassword(String username, String password) {
		try {
			Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
			return user.map(m -> modelMapper.map(m, UserDTO.class)).orElse(null);
		} catch (Exception e) {
			throw new RuntimeException("Unable to get user by Username and Password.", e);
		}
	}

	@Override
	public boolean checkLogin(String username, String password) {
		return findByUsernameAndPassword(username, password) != null;
	}

	@Override
	public UserDTO findByUsername(String username) {
		try {
			Optional<User> user = userRepository.findByUsername(username);
			return user.map(m -> modelMapper.map(m, UserDTO.class)).orElse(null);
		} catch (Exception e) {
			throw new RuntimeException("Unable to get user by Username.", e);
		}
	}
	
	@Override
	public List<GrantedAuthority> getAuthorities(String username) {
		try {
			Optional<User> user = userRepository.findByUsername(username);
			if(user.isPresent()) {
				return user.get().getRoles().stream().map(m -> new SimpleGrantedAuthority(m.getName())).collect(Collectors.toList());
			} else {
				return new ArrayList<GrantedAuthority>();
			}
		} catch (Exception e) {
			throw new RuntimeException("Unable to get user by Username.", e);
		}
	}

}
