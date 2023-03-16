package com.laptrinhjavaweb.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.converter.UserTestConverter;
import com.laptrinhjavaweb.dto.UserTestDTO;
import com.laptrinhjavaweb.entity.UserTestEntity;
import com.laptrinhjavaweb.repository.UserTestRepository;
import com.laptrinhjavaweb.service.IUserTestService;

@Service
public class UserTestService implements IUserTestService{
	@Autowired
	private UserTestRepository userTestRepository;
	
	@Autowired
	private UserTestConverter userTestConverter;
	
	@Override
	public UserTestDTO findByUsernameAndPassword(String username, String password) {
		Optional<UserTestEntity> option = userTestRepository.findByUsernameAndPassword(username, password);
		UserTestEntity userTestEntity = null;
		if(option.isPresent()) {
			userTestEntity = option.get();
		}
		return userTestConverter.toDTO(userTestEntity);
	}

	@Override
	public boolean checkLogin(String username, String password) {
		return findByUsernameAndPassword(username, password) != null;
	}

	@Override
	public UserTestDTO findByUsername(String username) {
		Optional<UserTestEntity> option = userTestRepository.findByUsername(username);
		UserTestEntity userTestEntity = null;
		if(option.isPresent()) {
			userTestEntity = option.get();
		}
		return userTestConverter.toDTO(userTestEntity);
	}

}
