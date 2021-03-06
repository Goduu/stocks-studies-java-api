package com.goduu.stocksstudies.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.goduu.stocksstudies.dto.AuthDTO;
import com.goduu.stocksstudies.dto.UserDTO;
import com.goduu.stocksstudies.dto.UserRegistryDTO;
import com.goduu.stocksstudies.models.User;
import com.goduu.stocksstudies.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity register(@RequestBody UserRegistryDTO objDto) throws UnsupportedEncodingException {
		User obj = service.fromRegistryDTO(objDto);
		obj = service.register(obj);
		if (obj == null || obj.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		Map<String, String> results = new HashMap<>();
		User user = service.authenticate(objDto.getEmail(), objDto.getPassword(), results);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(results);
		}
		return buildSuccess(results.get("authToken"), user);
	}

	private ResponseEntity<Map<String,Object>> buildSuccess(String authToken, User user) {
		Map<String,Object> response = new HashMap<String, Object>();
		response.put("authToken", authToken);
		response.put("info", user);
		return ResponseEntity.ok().body(response);
	}

	/**
	 * Authenticates the user by generationg a jwt token, counting the number 
	 * of users logged in and returning for the user the token and user infos
	 *
	 * @param login    - email and password of the user
	 * @param session  - sprin session control for counting users
	 * @return authToken with the jwt token and user infos
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/login")
	public ResponseEntity authenticateUser(@RequestBody AuthDTO login, HttpSession session) throws UnsupportedEncodingException {

		Integer counter = (Integer) session.getAttribute("count");

		if (counter == null) {
			counter = 1;
		} else {
			counter++;
		}

		session.setAttribute("count", counter);
		Map<String, String> results = new HashMap<>();

		User user = service.authenticate(login.getEmail(), login.getPassword(), results);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(results);
		}

		return buildSuccess(results.get("authToken"), user);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody UserDTO objDto, @PathVariable String id) {
		service.update(objDto);
		return ResponseEntity.noContent().build();
	}

	// @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
	// public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
	// User obj = service.findById(id);
	// return ResponseEntity.ok().body(obj.getPosts());
	// }
}
