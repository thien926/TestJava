package com.rungroop.web.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rungroop.web.dtos.ClubDto;
import com.rungroop.web.entities.Club;
import com.rungroop.web.repositories.ClubRepository;
import com.rungroop.web.services.ClubService;

@Service
public class ClubServiceImpl implements ClubService{
	@Autowired
	private ClubRepository clubRepository;
	
	@Override
	public List<ClubDto> findAllClubs() {
		List<Club> clubs = clubRepository.findAll();
		List<ClubDto> res = clubs.stream().map(club -> mapToClubDto(club)).collect(Collectors.toList());
		return res;
	}
	
	private ClubDto mapToClubDto(Club club) {
		ClubDto res = ClubDto.builder()
						.id(club.getId())
						.title(club.getTitle())
						.photoUrl(club.getPhotoUrl())
						.content(club.getContent())
						.createdOn(club.getCreatedOn())
						.updatedOn(club.getUpdatedOn())
						.build();
		return res;
	}
}
