package com.goduu.stocksstudies.services;

import java.util.List;
import java.util.Map;
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

	private int BUY = 1;
	private int SELL = 2;
	private int DIVIDEND = 3;

	public class OperationNotAllowedException extends Exception {
		public OperationNotAllowedException(String errorMessage) {
			super(errorMessage);
		}
	}

	@Autowired
	private OperationRepository repo;

	public List<Operation> findAllByUserId(String userId) {
		Optional<List<Operation>> obj = repo.findAllByUserId(userId);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public List<Operation> findAllByUserIdAndAsset(String userId, String asset) {
		Optional<List<Operation>> obj = repo.findAllByUserIdAndAsset(userId, asset);
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

	public Operation registerOperation(Operation obj) throws OperationNotAllowedException {
		if(validateOperation(obj)){
			return repo.insert(obj);
		} else{ 
			throw new OperationNotAllowedException("Not enough balance to sell");
		}
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

	// private Double summingShares(List<Double> shares) {
	// return shares.stream().reduce(0d, Double::sum);
	// }

	private Boolean validateOperation(Operation op) {
		if (op.getOperation() == SELL) {

			List<Operation> operations = findAllByUserIdAndAsset(op.getUserId(), op.getAsset());
			Double sum = operations.stream().mapToDouble(Operation::getShares).sum();
			return sum + op.getShares() >= 0 ? true : false;

		}
		return true;
	}

}
