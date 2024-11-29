package com.devteria.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devteria.identity_service.entity.User;

//@Repository
public interface UserRepository extends JpaRepository<User, String>{
	boolean existsByUsername(String username);
}
