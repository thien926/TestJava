package com.example.movie.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie.dto.MovieDTO;
import com.example.movie.entity.Movie;
import com.example.movie.repo.MovieRepository;
import com.example.movie.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public MovieDTO createMovie(MovieDTO movieDTO) {
		try {
			Movie movie = modelMapper.map(movieDTO, Movie.class);
			movie = movieRepository.save(movie);
            return modelMapper.map(movie, MovieDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create movie", e);
        }
	}

	public MovieDTO getMovieById(Long id) {
		try {
			Optional<Movie> movie = movieRepository.findById(id);
//			return movie.map(m -> modelMapper.map(m, MovieDTO.class)).orElseThrow(() -> new RuntimeException("Movie not found."));
			return movie.map(m -> modelMapper.map(m, MovieDTO.class)).orElse(null);
		} catch (Exception e) {
        	throw new RuntimeException("Unable to get movie by ID.", e);
        }
	}

	public MovieDTO updateMovie(MovieDTO movieDTO, Long id) {
		try {
//            Movie existingMovie = movieRepository.findById(id)
//                    .orElseThrow(() -> new RuntimeException("Movie not found."));
			Movie existingMovie = movieRepository.findById(id)
                    .orElse(null);
			
			if(existingMovie == null) {
				return null;
			}
			
            modelMapper.map(movieDTO, existingMovie);
            existingMovie.setId(id);
            existingMovie = movieRepository.save(existingMovie);
            return modelMapper.map(existingMovie, MovieDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Unable to update movie.", e);
        }
	}

	public boolean deleteMovie(Long id) {
		try {
			Movie movie = movieRepository.findById(id).orElse(null);
			if(movie == null) return false;
            movieRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete movie.", e);
        }
	}

	public List<MovieDTO> getAllMovies() {
		try {
            List<Movie> movies = movieRepository.findAll();
            return movies.stream()
                        .map(movie -> modelMapper.map(movie, MovieDTO.class))
                        .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Unable to get all movies.", e);
        }
	}

}