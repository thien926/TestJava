package com.rungroop.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.rungroop.web.dtos.ClubDto;
import com.rungroop.web.entities.Club;

@Mapper
public interface ClubMapper {
	ClubMapper INSTANCE = Mappers.getMapper(ClubMapper.class);
	
	Club toEntity(ClubDto clubDto);
	
	ClubDto toDto(Club club);
}
