package com.rungroop.web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rungroop.web.entities.Club;

public interface ClubRepository extends JpaRepository<Club, Long>{
	Optional<Club> findByTitle(String keyword);
	
	@Query("SELECT c FROM Club c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY id")
	List<Club> searchClubs(String query);
}
