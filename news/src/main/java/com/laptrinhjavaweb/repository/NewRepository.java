package com.laptrinhjavaweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laptrinhjavaweb.entity.NewEntity;

public interface NewRepository extends JpaRepository<NewEntity, Long> {
	Optional<NewEntity> findByContent(String content);

//	https://stackoverflow.com/questions/21456494/spring-jpa-query-with-like
	Optional<List<NewEntity>> findByContentContainingIgnoreCase(String content);
	Optional<List<NewEntity>> findByContentContainingIgnoreCaseAndTitleContainingIgnoreCase(String content, String title);
	
//	https://stackoverflow.com/questions/10696490/does-spring-data-jpa-have-any-way-to-count-entites-using-method-name-resolving
	long countByContentContainingIgnoreCase(String content);
}
