package com.devteria.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devteria.identity_service.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{
	boolean existsByUsername(String username);
	Optional<User> findByUsername(String username);
}
