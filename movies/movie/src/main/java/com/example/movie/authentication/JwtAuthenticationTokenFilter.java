package com.example.movie.authentication;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.example.movie.dto.UserDTO;
import com.example.movie.service.JwtService;
import com.example.movie.service.UserService;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
	private final static String TOKEN_HEADER = "authorization";
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
		throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authToken = httpServletRequest.getHeader(TOKEN_HEADER);
		if(!Optional.ofNullable(authToken).isEmpty()) {
			authToken = authToken.substring(7);
			
			if(jwtService.validateTokenLogin(authToken)) {
				String username = jwtService.getUsernameFromToken(authToken);
				
				UserDTO user = userService.findByUsername(username);
				if(user != null) {
					boolean enabled = true;
					boolean accountNonExpired = true;
					boolean credentialsNonExpired = true;
					boolean accountNonLocked = true;
					
					UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, userService.getAuthorities(username));
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}
		
		chain.doFilter(httpServletRequest, response);
	}
}