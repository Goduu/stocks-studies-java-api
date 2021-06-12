package com.goduu.stocksstudies.services;

import java.util.List;

import com.goduu.stocksstudies.models.Ticker;
import com.goduu.stocksstudies.repository.TickerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class TickerService {

	@Autowired
	private TickerRepository repo;

	public List<Ticker> findAllByDescriptionAndTickerAndExchange(String search, List<String> exchange, int pageSize){
		
		Pageable  pageable = PageRequest.of(0, pageSize);

		return repo.findAllByDescriptionAndTickerAndExchange(search, exchange, pageable).getContent();
	}

	

}
