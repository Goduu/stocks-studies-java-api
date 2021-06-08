package com.nelioalves.workshopmongo.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.nelioalves.workshopmongo.dto.UserDTO;
import com.nelioalves.workshopmongo.dto.UserRegistryDTO;
import com.nelioalves.workshopmongo.models.Post;
import com.nelioalves.workshopmongo.models.User;
import com.nelioalves.workshopmongo.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	private UserService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(obj));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Void> register(@RequestBody UserRegistryDTO objDto) {
		User obj = service.fromRegistryDTO(objDto);
		obj = service.register(obj);
		if (obj == null || obj.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody UserRegistryDTO objDto, @PathVariable String id) {
		User obj = service.fromRegistryDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	// @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
	// public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
	// 	User obj = service.findById(id);
	// 	return ResponseEntity.ok().body(obj.getPosts());
	// }
}
