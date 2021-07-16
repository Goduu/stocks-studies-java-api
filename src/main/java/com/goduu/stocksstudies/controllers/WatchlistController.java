package com.goduu.stocksstudies.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.goduu.stocksstudies.dto.GridDTO;
import com.goduu.stocksstudies.models.Grid;
import com.goduu.stocksstudies.models.Watchlist;
import com.goduu.stocksstudies.services.GridService;
import com.goduu.stocksstudies.services.WatchlistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/watchlist")
public class WatchlistController {

	@Autowired
	private WatchlistService service;

	@RequestMapping(value = "/fetch/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Watchlist> findAllByUser(@PathVariable String userId) {
		Watchlist list = service.findAllByUserId(userId);
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> update(@RequestBody Watchlist obj) {
		Watchlist res = service.update(obj);
		return ResponseEntity.ok().body(res.getId());
	}
	

}
