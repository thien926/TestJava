package com.rungroop.web.services;

import com.rungroop.web.dtos.EventDto;

public interface EventService {
	EventDto saveEvent(Long clubId, EventDto eventDto);
}
