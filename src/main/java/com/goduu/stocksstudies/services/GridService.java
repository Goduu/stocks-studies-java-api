package com.goduu.stocksstudies.services;

import java.util.List;
import java.util.Optional;

import com.goduu.stocksstudies.models.Grid;
import com.goduu.stocksstudies.repository.GridRepository;
import com.goduu.stocksstudies.services.exception.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class GridService {

	@Autowired
	private GridRepository repo;

	public List<Grid> findAllByUser(String userId) {
		return repo.findAllByUser(userId);
	}

	public Grid findById(String id) {
		Optional<Grid> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("ObjectNotFound"));
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

	private void updateData(Grid newObj, Grid obj) {
		newObj.setIdentifier(obj.getIdentifier());
		newObj.setUser(obj.getUser());
		newObj.setGridElements(obj.getGridElements());
	}
	
	public Grid update(Grid grid) {
		Optional<Grid> obj = repo.findById(grid.getId());
		if(obj.isPresent()) {
			updateData(grid, obj.get());
			return repo.save(grid);
		} else{
			return repo.insert(grid);
		}
		
	}

}
