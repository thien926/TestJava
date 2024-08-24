package com.rungroop.web.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rungroop.web.dtos.ClubDto;
import com.rungroop.web.entities.Club;
import com.rungroop.web.mappers.ClubMapper;
import com.rungroop.web.repositories.ClubRepository;
import com.rungroop.web.services.ClubService;

@Service
public class ClubServiceImpl implements ClubService{
	@Autowired
	private ClubRepository clubRepository;
	
	@Override
	public List<ClubDto> findAllClubs() {
		return clubRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
				.stream()
				.map(ClubMapper.INSTANCE::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public ClubDto saveClub(ClubDto clubDto) {
		Club club = ClubMapper.INSTANCE.toEntity(clubDto);
		clubRepository.save(club);
		return ClubMapper.INSTANCE.toDto(club);
	}

	@Override
	public ClubDto findById(long clubId) {
		return clubRepository.findById(clubId)
				.map(ClubMapper.INSTANCE::toDto)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Club Id: " + clubId));
	}

	@Override
	public void deleteById(long clubId) {
		clubRepository.deleteById(clubId);
	}

	@Override
	public List<ClubDto> searchClubs(String query) {
		return clubRepository.searchClubs(query)
				.stream()
				.map(ClubMapper.INSTANCE::toDto)
				.collect(Collectors.toList());
	}
}
