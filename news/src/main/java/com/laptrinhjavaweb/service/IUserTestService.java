package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.UserTestDTO;

public interface IUserTestService {
	UserTestDTO findByUsernameAndPassword(String username, String password);
	boolean checkLogin(String username, String password);
	UserTestDTO findByUsername(String username);
}
