package com.rungroop.web.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

	@Override
	public List<EventDto> findAll() {
		return eventRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
				.stream()
				.map(item -> modelMapper.map(item, EventDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<EventDto> searchEvents(String query) {
		if(StringUtils.isBlank(query)) {
			return eventRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
					.stream()
					.map(item -> modelMapper.map(item, EventDto.class))
					.collect(Collectors.toList());
		}
		
		return eventRepository.findByNameContainingIgnoreCase(query, Sort.by(Sort.Direction.ASC, "id"))
				.stream()
				.map(item -> modelMapper.map(item, EventDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public EventDto findById(Long eventId) {
		return modelMapper.map(eventRepository.findById(eventId).orElse(null), EventDto.class);
	}
}
