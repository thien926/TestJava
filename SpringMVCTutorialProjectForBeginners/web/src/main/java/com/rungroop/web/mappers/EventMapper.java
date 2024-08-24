package com.rungroop.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.rungroop.web.dtos.EventDto;
import com.rungroop.web.entities.Event;

@Mapper
public interface EventMapper {
	EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
	
	Event toEntity(EventDto eventDto);
	
	EventDto toDto(Event event);
}
