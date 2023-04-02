package com.example.movie.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	
	private Long id;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
}
