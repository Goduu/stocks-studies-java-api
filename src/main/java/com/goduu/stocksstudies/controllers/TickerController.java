package com.goduu.stocksstudies.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.goduu.stocksstudies.dto.TickerDTO;
import com.goduu.stocksstudies.models.Ticker;
import com.goduu.stocksstudies.services.TickerService;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/ticker")
public class TickerController {

	@Autowired
	private TickerService service;

	@RequestMapping(value = "/{exchange}/{search}", method = RequestMethod.GET)
	public ResponseEntity<List<TickerDTO>> findAllByDescriptionAndTickerAndExchange(@PathVariable String search,
			@PathVariable List<String> exchange) {
		if (search.equals("-all-")) {
			search = ".";
		}
		List<Ticker> list = service.findAllByDescriptionAndTickerAndExchange(search, exchange, 20);
		List<TickerDTO> listDto = list.stream().map(x -> new TickerDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/search/{search}", method = RequestMethod.GET)
	public ResponseEntity<List<TickerDTO>> fetchTickersBySearch(@PathVariable String search) {
		if (search.equals("-all-")) {
			search = ".";
		}
		List<Ticker> list = service.fetchTickersBySearch(search, 15);
		List<TickerDTO> listDto = list.stream().map(x -> new TickerDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/fetchTickersInfosByList", method = RequestMethod.POST)
	public ResponseEntity<List<Ticker>> fetchTickersBySearch(@RequestBody List<String> tickerList) {

		List<Ticker> list = service.fetchTickersInfosByList(tickerList, 15, 0, "ticker", "ASC");
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/fetchTickerData/{tickerString}", method = RequestMethod.GET)
	public ResponseEntity<Ticker> fetchTickerData(@PathVariable String tickerString) {

		Ticker ticker = service.fetchTickersInfos(tickerString);
		return ResponseEntity.ok().body(ticker);
	}

	@RequestMapping(value = "trending/{exchange}", method = RequestMethod.GET)
	public ResponseEntity<List<TickerDTO>> findTreddingByExchange(@PathVariable String exchange)
			throws JsonIOException, JsonSyntaxException, IOException {

		List<TickerDTO> list = service.findTreddingByCountry(exchange);
		return ResponseEntity.ok().body(list);
	}

}
