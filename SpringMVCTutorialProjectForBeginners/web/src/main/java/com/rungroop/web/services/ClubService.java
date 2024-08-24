package com.rungroop.web.services;

import java.util.List;

import com.rungroop.web.dtos.ClubDto;

public interface ClubService {
	List<ClubDto> findAllClubs();
	ClubDto saveClub(ClubDto clubDto);
	ClubDto findById(long clubId);
}
