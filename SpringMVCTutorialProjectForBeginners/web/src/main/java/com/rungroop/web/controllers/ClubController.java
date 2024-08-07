package com.rungroop.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rungroop.web.dtos.ClubDto;
import com.rungroop.web.services.ClubService;

@Controller
public class ClubController {
	@Autowired
	private ClubService clubService;
	
	@GetMapping("/clubs")
	public ModelAndView listClubs(ModelAndView mav) {
		List<ClubDto> clubs = clubService.findAllClubs();
		mav.addObject("clubs", clubs);
		mav.setViewName("clubs/club-list");
		return mav;
	}
}
