package com.rungroop.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rungroop.web.dtos.ClubDto;
import com.rungroop.web.entities.Club;
import com.rungroop.web.mappers.ClubMapper;
import com.rungroop.web.services.ClubService;

import jakarta.validation.Valid;

@Controller
public class ClubController {
	@Autowired
	private ClubService clubService;
	
	// GetAll
	@GetMapping("/clubs")
	public ModelAndView listClubs(ModelAndView mav) {
		List<ClubDto> clubs = clubService.findAllClubs();
		mav.addObject("clubs", clubs);
		mav.setViewName("clubs/clubs-list");
		return mav;
	}
	
	// Create
	@GetMapping("/clubs/new")
	public ModelAndView createClubForm(ModelAndView mav) {
		Club club = new Club();
		mav.addObject("club", ClubMapper.INSTANCE.toDto(club));
		mav.setViewName("clubs/clubs-create");
		return mav;
	}
	
	@PostMapping("/clubs/new")
	public ModelAndView saveCreationClub(@Valid @ModelAttribute("club") ClubDto clubDto
			, BindingResult bindingResult
			, ModelAndView mav) {
		if (bindingResult.hasErrors()) {
			mav.addObject("club", clubDto);
			mav.setViewName("clubs/clubs-create");
			return mav;
		}
		clubService.saveClub(clubDto);
		mav.setViewName("redirect:/clubs");
		return mav;
	}
	
	// Update
	@GetMapping("/clubs/{clubId}/edit")
	public ModelAndView editClubForm(@PathVariable("clubId") long clubId
			, ModelAndView mav) {
		ClubDto dto = clubService.findById(clubId);
		mav.addObject("club", dto);
		mav.setViewName("clubs/clubs-edit");
		return mav;
	}
	
	@PostMapping("/clubs/{clubId}/edit")
	public ModelAndView saveEditionClub(@PathVariable("clubId") long clubId
			, @Valid @ModelAttribute("club") ClubDto clubDto
			, BindingResult bindingResult
			, ModelAndView mav) {
		if (bindingResult.hasErrors()) {
			clubDto.setId(clubId);
			mav.addObject("club", clubDto);
			mav.setViewName("clubs/clubs-edit");
			return mav;
		}
		clubService.saveClub(clubDto);
		mav.setViewName("redirect:/clubs");
		return mav;
	}
	
	//Detail
	@GetMapping("/clubs/{clubId}")
	public ModelAndView clubDetail(@PathVariable("clubId") long clubId
			, ModelAndView mav) {
		ClubDto dto = clubService.findById(clubId);
		mav.addObject("club", dto);
		mav.setViewName("clubs/clubs-detail");
		return mav;
	}
}
