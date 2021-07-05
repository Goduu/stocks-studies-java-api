package com.goduu.stocksstudies.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	public List<String> findAllIdentifiersByUser(String userId) {
		List<Grid> gridList = repo.findAllIdentifiersByUser(userId);
		return gridList.stream().map(g -> g.getIdentifier()).collect(Collectors.toList());

	}

	public Grid findById(String id) {
		Optional<Grid> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("ObjectNotFound"));
	}

	/**
	 * Delete a grid by id
	 * 
	 * @param id
	 */
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

	/**
	 * Update a grid data atributes before saving it
	 * 
	 * @param newObj Grid to be updated to
	 * @param obj    Grid to be updated from
	 */
	private void updateData(Grid newObj, Grid obj) {
		newObj.setIdentifier(obj.getIdentifier());
		newObj.setUserId(obj.getUserId());
		newObj.setGridElements(obj.getGridElements());
		newObj.setActive(true);
	}

	public Grid insert(Grid obj) {
		return repo.insert(obj);
	}

	/**
	 * Update or insert a new grid
	 * 
	 * @param grid
	 * @return saved grid
	 */
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

	/**
	 * Set a grid as active
	 * 
	 * @param grid
	 * @return saved grid
	 */
	public Grid deactivateGrid(String userId, String identifier) {
		Optional<Grid> obj = repo.findAllByUserIdAndIdentifier(userId, identifier);
		if (obj.isPresent()) {
			obj.get().setActive(false);
			return repo.save(obj.get());
		}
		return null;

	}

	/**
	 * Set a new Grid from a DTO
	 * 
	 * @param objDto
	 * @return
	 */
	public Grid fromRegistryDTO(GridDTO objDto) {
		Grid grid = new Grid();
		grid.setId(objDto.getId());
		grid.setIdentifier(objDto.getIdentifier());
		grid.setUserId(objDto.getUserId());
		grid.setGridElements(objDto.getGridElements());

		return grid;
	}

}
