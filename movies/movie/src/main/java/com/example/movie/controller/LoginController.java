package com.example.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie.dto.UserDTO;
import com.example.movie.service.JwtService;
import com.example.movie.service.UserService;

@RestController
@RequestMapping(value = "/api")
public class LoginController {
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDTO dto) {
		String result = "";
		if(userService.checkLogin(dto.getUsername(), dto.getPassword())) {
			result = jwtService.generateTokenLogin(dto.getUsername());
		}
		return ResponseEntity.ok(result);
	}
}
