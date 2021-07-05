package com.goduu.stocksstudies.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.goduu.stocksstudies.dto.GridDTO;
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
		List<Grid> list = service.findAllByUserId(userId);
		List<GridDTO> listDto = list.stream().map(x -> new GridDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/{userId}/tickers", method = RequestMethod.GET)
	public ResponseEntity<List<String>> findAllIdentifiersByUser(@PathVariable String userId) {
		List<String> list = service.findAllIdentifiersByUser(userId);
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> update(@PathVariable String id, @RequestBody GridDTO objDto) {
		Grid obj = service.fromRegistryDTO(objDto);
		Grid res = service.update(obj);
		return ResponseEntity.ok().body(res.getId());
	}
	
	@RequestMapping(value = "/deactivateGrid", method = RequestMethod.PUT)
	public ResponseEntity<String> deactivateGrid(@RequestBody GridDTO objDto) {
		
		service.deactivateGrid(objDto.getUserId(), objDto.getIdentifier());

		return ResponseEntity.noContent().build();

	}
	

}
