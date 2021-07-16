package com.goduu.stocksstudies.services;

import java.util.Optional;

import com.goduu.stocksstudies.models.Watchlist;
import com.goduu.stocksstudies.repository.WatchlistRepository;
import com.goduu.stocksstudies.services.exception.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class WatchlistService {

	@Autowired
	private WatchlistRepository repo;

	public Watchlist findAllByUserId(String userId) {
		Optional<Watchlist> obj =  repo.findAllByUserId(userId);
		return obj.orElseThrow(() -> new ObjectNotFoundException("ObjectNotFound"));
	}


	public Watchlist findById(String id) {
		Optional<Watchlist> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("ObjectNotFound"));
	}

	/**
	 * Delete a Watchlist by id
	 * 
	 * @param id
	 */
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

	/**
	 * Update a Watchlist data atributes before saving it
	 * 
	 * @param newObj Watchlist to be updated to
	 * @param obj    Watchlist to be updated from
	 */
	private void updateData(Watchlist newObj, Watchlist obj) {
		newObj.setUserId(obj.getUserId());
		newObj.setList(obj.getList());
	}

	public Watchlist insert(Watchlist obj) {
		return repo.insert(obj);
	}

	/**
	 * Update or insert a new Watchlist
	 * 
	 * @param watchlist
	 * @return saved Watchlist
	 */
	public Watchlist update(Watchlist watchlist) {
		if (watchlist.getId() != null) {
			Optional<Watchlist> obj = repo.findById(watchlist.getId());
			if (obj.isPresent()) {
				updateData(obj.get(), watchlist);
				return repo.save(obj.get());
			}
		} else {
			Optional<Watchlist> obj = repo.findAllByUserId(watchlist.getUserId());
			if (!obj.isPresent()) {
				return repo.insert(watchlist);
			}
		}
		return null;

	}


}
