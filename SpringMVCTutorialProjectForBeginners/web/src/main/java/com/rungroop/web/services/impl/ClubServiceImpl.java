package com.rungroop.web.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rungroop.web.dtos.ClubDto;
import com.rungroop.web.entities.Club;
import com.rungroop.web.repositories.ClubRepository;
import com.rungroop.web.services.ClubService;

@Service
public class ClubServiceImpl implements ClubService{
	@Autowired
	private ClubRepository clubRepository;
	
	@Autowired
    private ModelMapper modelMapper;	
	
	@Override
	public List<ClubDto> findAllClubs() {
		return clubRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
				.stream()
				.map(club -> modelMapper.map(club, ClubDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public ClubDto saveClub(ClubDto clubDto) {
		Club club = modelMapper.map(clubDto, Club.class);
		clubRepository.save(club);
		return modelMapper.map(club, ClubDto.class);
	}

	@Override
	public ClubDto findById(long clubId) {
		return clubRepository.findById(clubId)
				.map(club -> modelMapper.map(club, ClubDto.class))
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
				.map(club -> modelMapper.map(club, ClubDto.class))
				.collect(Collectors.toList());
	}
}
