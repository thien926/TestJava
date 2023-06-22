package com.example.mapper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
	
	@GetMapping(value = "")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("message", "This is the home page.");
        return modelAndView;
	}
}
