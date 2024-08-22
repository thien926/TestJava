package com.rungroop.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rungroop.web.dtos.ClubDto;
import com.rungroop.web.entities.Club;
import com.rungroop.web.mappers.ClubMapper;
import com.rungroop.web.services.ClubService;

@Controller
public class ClubController {
	@Autowired
	private ClubService clubService;
	
	@GetMapping("/clubs")
	public ModelAndView listClubs(ModelAndView mav) {
		List<ClubDto> clubs = clubService.findAllClubs();
		mav.addObject("clubs", clubs);
		mav.setViewName("clubs/clubs-list");
		return mav;
	}
	
	@GetMapping("/clubs/new")
	public ModelAndView createClubForm(ModelAndView mav) {
		Club club = new Club();
		mav.addObject("club", ClubMapper.INSTANCE.toDto(club));
		mav.setViewName("clubs/clubs-create");
		return mav;
	}
	
	@PostMapping("/clubs/new")
	public ModelAndView saveClub(@ModelAttribute("club") ClubDto clubDto
			, ModelAndView mav) {
		clubService.saveClub(clubDto);
		mav.setViewName("redirect:/clubs");
		return mav;
	}
}
