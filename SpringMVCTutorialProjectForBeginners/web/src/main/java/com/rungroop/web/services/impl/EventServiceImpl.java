package com.rungroop.web.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rungroop.web.dtos.EventDto;
import com.rungroop.web.entities.Club;
import com.rungroop.web.entities.Event;
import com.rungroop.web.repositories.ClubRepository;
import com.rungroop.web.repositories.EventRepository;
import com.rungroop.web.services.EventService;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired 
	private ClubRepository clubRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Override
	public EventDto saveEvent(Long clubId, EventDto eventDto) {
		Club club = clubRepository.findById(clubId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Club Id: " + clubId));
		Event event = modelMapper.map(eventDto, Event.class);
		event.setClub(club);
		eventRepository.save(event);
		return modelMapper.map(event, EventDto.class);
	}

}
