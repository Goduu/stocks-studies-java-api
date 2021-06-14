package com.goduu.stocksstudies.services;

import java.util.List;
import java.util.Optional;

import com.goduu.stocksstudies.models.Operation;
import com.goduu.stocksstudies.repository.OperationRepository;
import com.goduu.stocksstudies.services.exception.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class OperationService {

	@Autowired
	private OperationRepository repo;

	public List<Operation> findAllByUserId(String userId) {
		Optional<List<Operation>> obj = repo.findAllByUserId(userId);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public Operation findById(String id) {
		Optional<Operation> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

	public Operation registerOperation(Operation obj) {
		
		return repo.insert(obj);
	}

	public Operation update(Operation obj) {
		Operation dbObjt = findById(obj.getId());
		updateData(dbObjt, obj);
		return repo.save(dbObjt);
	}

	private void updateData(Operation dbObjt, Operation obj) {
		dbObjt.setAsset(obj.getAsset());
		dbObjt.setDate(obj.getDate());
		dbObjt.setOperation(obj.getOperation());
		dbObjt.setValue(obj.getValue());
		dbObjt.setShares(obj.getShares());
		dbObjt.setUserId(obj.getUserId());
	}

}
