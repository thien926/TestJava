package com.rungroop.web.services;

import java.util.List;

import com.rungroop.web.dtos.EventDto;

public interface EventService {
	EventDto saveEvent(Long clubId, EventDto eventDto);
	
	List<EventDto> findAll();
	
	List<EventDto> searchEvents(String query);
	
	EventDto findById(Long eventId);
}
