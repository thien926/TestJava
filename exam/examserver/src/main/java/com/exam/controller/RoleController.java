package com.exam.controller;

import java.net.URI;
import java.util.List;

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
import com.exam.service.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping(value = "/role")
	public ResponseEntity<List<Role>> getAll() {
		return new ResponseEntity<List<Role>>(roleService.getAll(), HttpStatusCode.valueOf(200));
	}
	
	@GetMapping(value = "/role/{id}")
	public ResponseEntity<Role> findById(@PathVariable(value = "id") Long id) {
		Role role = roleService.findById(id);
	    if (role != null) {
	        return ResponseEntity.ok().body(role);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PostMapping(value = "/role")
	public ResponseEntity<Role> save(@RequestBody Role role) {
		Role savedRole = roleService.save(role, null);
	    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	                    .buildAndExpand(savedRole.getId()).toUri();
	    return ResponseEntity.created(location).body(savedRole);
	}
	
	@PutMapping(value = "/role/{id}")
	public ResponseEntity<Role> update(@PathVariable(value = "id") Long id, @RequestBody Role role) {
		Role updatedRole = roleService.save(role, id);
	    if (updatedRole != null) {
	        return ResponseEntity.ok().body(updatedRole);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@DeleteMapping(value = "/role/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		roleService.deleteById(id);
	    return ResponseEntity.noContent().build();
	}
}
