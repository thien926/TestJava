package com.rungroop.web.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rungroop.web.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	@Query("SELECT c FROM Event c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY id")
	List<Event> searchEvents(String query);
	
	List<Event> findByNameContainingIgnoreCase(@Param("query") String query, Sort sort);
}
