package com.goduu.stocksstudies.services;

import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import com.goduu.stocksstudies.models.Ticker;
import com.goduu.stocksstudies.repository.TickerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class TickerService {

	@Autowired
	private TickerRepository repo;

	public List<Ticker> findAllByDescriptionAndTickerAndExchange(String search, String exchange){
		return repo.findAllByDescriptionAndTickerAndExchange(search, exchange);
	}

	

}
