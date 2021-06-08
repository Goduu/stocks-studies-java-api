package com.nelioalves.workshopmongo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.nelioalves.workshopmongo.dto.GridDTO;
import com.nelioalves.workshopmongo.models.Grid;
import com.nelioalves.workshopmongo.services.GridService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/grid")
public class GridController {

	@Autowired
	private GridService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<GridDTO>> findAllByUser(@PathVariable String userId) {
		List<Grid> list = service.findAllByUser(userId);
		List<GridDTO> listDto = list.stream().map(x -> new GridDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	

}
