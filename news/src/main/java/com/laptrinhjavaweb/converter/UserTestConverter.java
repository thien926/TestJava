package com.laptrinhjavaweb.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.laptrinhjavaweb.dto.UserTestDTO;
import com.laptrinhjavaweb.entity.UserTestEntity;

@Component
public class UserTestConverter {
	public UserTestEntity toEntity(UserTestDTO dto) {
		UserTestEntity entity = null;
		if(dto != null) {
			entity = new UserTestEntity();
			entity.setUsername(dto.getUsername());
			entity.setPassword(dto.getPassword());
			entity.setRolename(dto.getRolename());
		}
		return entity;
	}
	
	public UserTestDTO toDTO(UserTestEntity entity) {
		UserTestDTO dto = null;
		if(entity != null) {
			dto = new UserTestDTO();
			dto.setId(entity.getId());
			dto.setUsername(entity.getUsername());
			dto.setPassword(entity.getPassword());
			dto.setRolename(entity.getRolename());
		}
		return dto;
	}
	
	public UserTestEntity toEntity(UserTestDTO dto, UserTestEntity entity) {
		entity.setUsername(dto.getUsername());
		entity.setPassword(dto.getPassword());
		entity.setRolename(dto.getRolename());
		return entity;
	}
	
	public List<UserTestDTO> toListDTO(List<UserTestEntity> entities) {
		List<UserTestDTO> res = new ArrayList<>();
		for (UserTestEntity item : entities) {
			res.add(toDTO(item));
		}
		return res;
	}
}
