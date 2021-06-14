package com.goduu.stocksstudies.controllers;

import java.util.List;

import com.goduu.stocksstudies.models.Operation;
import com.goduu.stocksstudies.services.OperationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/operation")
public class OperationController {
	
	@Autowired
	private OperationService service;


	@RequestMapping(value = "/getAllOperations/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<Operation>> findAllByUserId(@PathVariable String userId) {
		List<Operation> objs = service.findAllByUserId(userId);
		return ResponseEntity.ok().body(objs);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/registerOperation", method = RequestMethod.POST)
	public ResponseEntity<Operation> registerOperation(@RequestBody Operation objDto) {
		Operation obj = service.registerOperation(objDto);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Operation objDto) {
		service.update(objDto);
		return ResponseEntity.noContent().build();
	}

	// @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
	// public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
	// User obj = service.findById(id);
	// return ResponseEntity.ok().body(obj.getPosts());
	// }
}
