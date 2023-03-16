package com.laptrinhjavaweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.dto.UserTestDTO;
import com.laptrinhjavaweb.response.Response;
import com.laptrinhjavaweb.service.impl.JwtService;
import com.laptrinhjavaweb.service.impl.UserTestService;

@RestController
public class LoginController {
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserTestService userTestService;
	
	@PostMapping("/login")
	public Response<String> login(@RequestBody UserTestDTO dto) {
		String result = null;
		try {
			if(userTestService.checkLogin(dto.getUsername(), dto.getPassword())) {
				result = jwtService.generateTokenLogin(dto.getUsername());
			}
		} catch (Exception e) {
			return new Response<>(false, new String[] { e.getMessage() });
		}
		
		if(result == null) {
			return new Response<>(false, new String[] { "Wrong username and password" });
		}
		return new Response<String>(true, null, null, result);
	}
}
