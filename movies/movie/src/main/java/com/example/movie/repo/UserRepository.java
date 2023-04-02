package com.example.movie.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movie.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	Optional<User> findByUsernameAndPassword(String username, String password);
}
