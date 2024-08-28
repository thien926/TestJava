package com.rungroop.web.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rungroop.web.dtos.ClubDto;
import com.rungroop.web.entities.Club;
import com.rungroop.web.services.ClubService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ClubController {
	@Autowired
	private ClubService clubService;
	
	@Autowired
    private ModelMapper modelMapper;
	
	// GetAll
	@GetMapping("/clubs")
	public ModelAndView listClubs(ModelAndView mav) {
		try {
			List<ClubDto> clubs = clubService.findAllClubs();
			mav.addObject("clubs", clubs);
			mav.setViewName("clubs/clubs-list");
		} catch (Exception e) {
			log.error("Error occurred while saving club: ", e);
			mav.addObject("errorMessage", "An error occurred while getting the clubs.");
            mav.addObject("errorDetails", e.getMessage());
            mav.setViewName("layouts/error");
		}
		return mav;
	}
	
	// Create
	@GetMapping("/clubs/new")
	public ModelAndView createClubForm(ModelAndView mav) {
		Club club = new Club();
		mav.addObject("club", modelMapper.map(club, ClubDto.class));
		mav.setViewName("clubs/clubs-create");
		return mav;
	}
	
	@PostMapping("/clubs/new")
	public ModelAndView saveCreationClub(@Valid @ModelAttribute("club") ClubDto clubDto
			, BindingResult bindingResult
			, ModelAndView mav) {
		try {
			if (bindingResult.hasErrors()) {
				mav.addObject("club", clubDto);
				mav.setViewName("clubs/clubs-create");
				return mav;
			}
			clubService.saveClub(clubDto);
			mav.setViewName("redirect:/clubs");
		} catch (Exception ex) {
			log.error("Error occurred while saving club: ", ex);
            mav.addObject("errorMessage", "An error occurred while saving the club.");
            mav.addObject("errorDetails", ex.getMessage());
            mav.setViewName("layouts/error");
		}
		
		return mav;
	}
	
	// Update
	@GetMapping("/clubs/{clubId}/edit")
	public ModelAndView editClubForm(@PathVariable("clubId") long clubId
			, ModelAndView mav) {
		try {
			ClubDto dto = clubService.findById(clubId);
			mav.addObject("club", dto);
			mav.setViewName("clubs/clubs-edit");
		} catch (Exception ex) {
			log.error("Error occurred while editing club: ", ex);
            mav.addObject("errorMessage", "An error occurred while editing the club.");
            mav.addObject("errorDetails", ex.getMessage());
            mav.setViewName("layouts/error");
		}
		
		return mav;
	}
	
	@PostMapping("/clubs/{clubId}/edit")
	public ModelAndView saveEditionClub(@PathVariable("clubId") long clubId
			, @Valid @ModelAttribute("club") ClubDto clubDto
			, BindingResult bindingResult
			, ModelAndView mav) {
		try {
			if (bindingResult.hasErrors()) {
				clubDto.setId(clubId);
				mav.addObject("club", clubDto);
				mav.setViewName("clubs/clubs-edit");
				return mav;
			}
			clubService.saveClub(clubDto);
			mav.setViewName("redirect:/clubs");
		} catch (Exception ex) {
			log.error("Error occurred while editing club: ", ex);
            mav.addObject("errorMessage", "An error occurred while editing the club.");
            mav.addObject("errorDetails", ex.getMessage());
            mav.setViewName("layouts/error");
		}
		
		return mav;
	}
	
	//Detail
	@GetMapping("/clubs/{clubId}")
	public ModelAndView clubDetail(@PathVariable("clubId") long clubId
			, ModelAndView mav) {
		try {
			ClubDto dto = clubService.findById(clubId);
			mav.addObject("club", dto);
			mav.setViewName("clubs/clubs-detail");
		} catch (Exception ex) {
			log.error("Error occurred while getting detail club: ", ex);
            mav.addObject("errorMessage", "An error occurred while getting the detail club.");
            mav.addObject("errorDetails", ex.getMessage());
            mav.setViewName("layouts/error");
		}
		
		return mav;
	}
	
	//Delete
	@GetMapping("/clubs/{clubId}/delete")
	public ModelAndView deleteClub(@PathVariable("clubId") long clubId
			, ModelAndView mav) {
		try {
			clubService.deleteById(clubId);
			mav.setViewName("redirect:/clubs");
		} catch (Exception ex) {
			log.error("Error occurred while deleting club: ", ex);
            mav.addObject("errorMessage", "An error occurred while deleting the club.");
            mav.addObject("errorDetails", ex.getMessage());
            mav.setViewName("layouts/error");
		}
		
		return mav;
	}
	
	@GetMapping("/clubs/search")
	public ModelAndView searchClub(@RequestParam("query") String query
			, ModelAndView mav) {
		try {
			List<ClubDto> clubs = clubService.searchClubs(query);
			mav.addObject("clubs", clubs);
			mav.setViewName("clubs/clubs-list");
		} catch (Exception ex) {
			log.error("Error occurred while searching clubs: ", ex);
            mav.addObject("errorMessage", "An error occurred while searching the clubs.");
            mav.addObject("errorDetails", ex.getMessage());
            mav.setViewName("layouts/error");
		}
		return mav;
	}
}
