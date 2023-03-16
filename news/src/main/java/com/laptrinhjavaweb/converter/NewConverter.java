package com.laptrinhjavaweb.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.laptrinhjavaweb.dto.NewDTO;
import com.laptrinhjavaweb.entity.NewEntity;

@Component
public class NewConverter {
	
	public NewEntity toEntity(NewDTO dto) {
		NewEntity entity = null;
		if(dto != null) {
			entity = new NewEntity();
			entity.setTitle(dto.getTitle());
			entity.setThumbnail(dto.getThumbnail());
			entity.setContent(dto.getContent());
			entity.setShortDescription(dto.getShortDescription());
		}
		return entity;
	}
	
	public NewDTO toDTO(NewEntity entity) {
		NewDTO dto = null;
		if(entity != null) {
			dto = new NewDTO();
			dto.setId(entity.getId());
			dto.setTitle(entity.getTitle());
			dto.setThumbnail(entity.getThumbnail());
			dto.setContent(entity.getContent());
			dto.setShortDescription(entity.getShortDescription());
			dto.setCreatedBy(entity.getCreatedBy());
			dto.setCreatedDate(entity.getCreatedDate());
			dto.setModifiedBy(entity.getModifiedBy());
			dto.setModifiedDate(entity.getModifiedDate());
		}
		return dto;
	}
	
	public NewEntity toEntity(NewDTO dto, NewEntity entity) {
		entity.setTitle(dto.getTitle());
		entity.setThumbnail(dto.getThumbnail());
		entity.setContent(dto.getContent());
		entity.setShortDescription(dto.getShortDescription());
		return entity;
	}
	
	public List<NewDTO> toListDTO(List<NewEntity> entities) {
		List<NewDTO> res = new ArrayList<>();
		for (NewEntity item : entities) {
			res.add(toDTO(item));
		}
		return res;
	}
}
