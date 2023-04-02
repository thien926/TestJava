package com.example.movie.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.movie.dto.MovieDTO;
import com.example.movie.service.MovieService;

@RestController
@RequestMapping(value = "/api")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@GetMapping("/movie")
	public ResponseEntity<List<MovieDTO>> getAllMovies() {
		return ResponseEntity.ok(movieService.getAllMovies());
	}

	@GetMapping("/movie/{id}")
	public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
		MovieDTO movieDTO = movieService.getMovieById(id);
		return (movieDTO != null) ? ResponseEntity.ok(movieDTO) : ResponseEntity.notFound().build();
	}

	@PostMapping("/movie")
	public ResponseEntity<Object> createMovie(@RequestBody @Valid MovieDTO movieDTO, BindingResult result) {
		if (result.hasErrors()) {
	        List<String> errors = result.getAllErrors().stream()
	                .map(DefaultMessageSourceResolvable::getDefaultMessage)
	                .collect(Collectors.toList());
	        return ResponseEntity.badRequest().body(errors);
	    }

		MovieDTO savedMovieDTO = movieService.createMovie(movieDTO);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedMovieDTO.getId()).toUri();
		return ResponseEntity.created(location).body(savedMovieDTO);
	}

	@PutMapping("/movie/{id}")
	public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody @Valid MovieDTO movieDTO) {
		MovieDTO updatedMovieDTO = movieService.updateMovie(movieDTO, id);
		if (updatedMovieDTO != null) {
			return ResponseEntity.ok(updatedMovieDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/movie/{id}")
	public ResponseEntity<MovieDTO> deleteMovie(@PathVariable Long id) {
		boolean isDeleted = movieService.deleteMovie(id);
		if (isDeleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
