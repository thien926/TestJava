package com.laptrinhjavaweb.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTestDTO {
	private Long id;
	
	@NotBlank(message = "Title không được thiếu")
	private String username;
	
	@NotBlank(message = "Title không được thiếu")
	private String password;
	
	private String rolename;
	
	@Transient
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(getRolename()));
		return authorities;
	}
}
