package com.laptrinhjavaweb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laptrinhjavaweb.entity.UserTestEntity;

public interface UserTestRepository extends JpaRepository<UserTestEntity, Long>{
	Optional<UserTestEntity> findByUsernameAndPassword(String username, String password);
	Optional<UserTestEntity> findByUsername(String username);
}
