package com.goduu.stocksstudies.services;

import java.util.List;
import java.util.Optional;

import com.goduu.stocksstudies.dto.GridDTO;
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

	public List<Grid> findAllByUserId(String userId) {
		return repo.findAllByUserId(userId);
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
		newObj.setUserId(obj.getUserId());
		newObj.setGridElements(obj.getGridElements());
		newObj.setActive(true);
	}

	public Grid insert(Grid obj) {
		return repo.insert(obj);
	}

	public Grid update(Grid grid) {
		if (grid.getId() != null) {
			Optional<Grid> obj = repo.findById(grid.getId());
			if (obj.isPresent()) {
				updateData(obj.get(), grid);
				return repo.save(obj.get());
			}
		} else {
			Optional<Grid> obj = repo.findAllByUserIdAndIdentifier(grid.getUserId(), grid.getIdentifier());
			if (!obj.isPresent()) {
				grid.setActive(true);
				return repo.insert(grid);
			}
		}
		return null;

	}

	public Grid fromRegistryDTO(GridDTO objDto) {
		Grid grid = new Grid();
		grid.setId(objDto.getId());
		grid.setIdentifier(objDto.getIdentifier());
		grid.setUserId(objDto.getUserId());
		grid.setGridElements(objDto.getGridElements());

		return grid;
	}

}
