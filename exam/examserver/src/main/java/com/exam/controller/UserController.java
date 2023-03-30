package com.exam.controller;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.RoleService;
import com.exam.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/user")
	public ResponseEntity<List<User>> getAll() {
		return new ResponseEntity<List<User>>(userService.getAll(), HttpStatusCode.valueOf(200));
	}
	
	@GetMapping(value = "/user/{username}")
	public ResponseEntity<User> findById(@PathVariable(value = "username") String username) {
		User user = userService.findByUsername(username);
	    if (user != null) {
	        return ResponseEntity.ok().body(user);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PostMapping(value = "/user/{roleId}")
	public ResponseEntity<User> save(@RequestBody User user, @PathVariable("roleId") Long roleId) {
		User savedUser = userService.save(user, null, roleId);
	    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
	                    .buildAndExpand(savedUser.getUsername()).toUri();
	    return ResponseEntity.created(location).body(savedUser);
	}
	
	@PutMapping(value = "/user/{id}")
	public ResponseEntity<User> update(@PathVariable(value = "id") Long id, @RequestBody User user) {
		User updatedUser = userService.save(user, id, null);
	    if (updatedUser != null) {
	        return ResponseEntity.ok().body(updatedUser);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		userService.deleteById(id);
	    return ResponseEntity.noContent().build();
	}
}
