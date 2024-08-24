package com.rungroop.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rungroop.web.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
