package com.goduu.stocksstudies.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.goduu.stocksstudies.dto.GridDTO;
import com.goduu.stocksstudies.dto.UserRegistryDTO;
import com.goduu.stocksstudies.models.Grid;
import com.goduu.stocksstudies.services.GridService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/grid")
public class GridController {

	@Autowired
	private GridService service;

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable String id, @RequestBody UserRegistryDTO objDto) {
		// service.update(id);
		return ResponseEntity.noContent().build();
	}
	

}
