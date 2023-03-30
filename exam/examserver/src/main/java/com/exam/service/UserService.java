package com.exam.service;

import java.util.List;

import com.exam.model.User;

public interface UserService {
	public User save(User user, Long id, Long roleId);
	public User findByUsername(String username);
	public void deleteById(Long userId);
	public List<User> getAll();
}
