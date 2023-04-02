package com.example.movie.service;

public interface JwtService {
	String generateTokenLogin(String username);
	String getUsernameFromToken(String token);
	Boolean validateTokenLogin(String token);
}
