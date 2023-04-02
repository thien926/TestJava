package com.example.movie.service;

import java.util.List;

import com.example.movie.dto.MovieDTO;

public interface MovieService {
	MovieDTO createMovie(MovieDTO movieDTO);
	MovieDTO getMovieById(Long id);
	MovieDTO updateMovie(MovieDTO movieDTO, Long id);
	boolean deleteMovie(Long id);
	List<MovieDTO> getAllMovies();
}
