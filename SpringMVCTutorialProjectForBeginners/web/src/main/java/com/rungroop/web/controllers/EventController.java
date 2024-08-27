package com.rungroop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rungroop.web.dtos.EventDto;
import com.rungroop.web.services.EventService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EventController {
	@Autowired
	private EventService eventService;
	
	@GetMapping("/events/{clubId}/new")
	public ModelAndView createEventForm(@PathVariable Long clubId
			, ModelAndView mav) {
		EventDto event = new EventDto();
		mav.addObject("clubId", clubId);
		mav.addObject("event", event);
		mav.setViewName("events/events-create");
		return mav;
	}
	
	@PostMapping("/events/{clubId}/new")
	public ModelAndView saveCreateEventForm(@PathVariable Long clubId
			, @Valid @ModelAttribute("event") EventDto eventDto
			, BindingResult bindingResult
			, ModelAndView mav) {
		try {
			if(bindingResult.hasErrors()) {
				mav.addObject("event", eventDto);
				mav.setViewName("events/events-create");
				return mav;
			}
			eventService.saveEvent(clubId, eventDto);
			mav.setViewName("redirect:/clubs/" + clubId);
		} catch (Exception ex) {
			log.error("Error occurred while creating event: ", ex);
            mav.addObject("errorMessage", "An error occurred while creating the event.");
            mav.addObject("errorDetails", ex.getMessage());
            mav.setViewName("layouts/error");
		}
		return mav;
	}
}
