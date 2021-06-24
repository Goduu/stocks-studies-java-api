package com.goduu.stocksstudies.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.goduu.stocksstudies.dto.PortifolioDTO;
import com.goduu.stocksstudies.dto.PortifolioElement;
import com.goduu.stocksstudies.dto.TimeseriesDTO;
import com.goduu.stocksstudies.models.Operation;
import com.goduu.stocksstudies.repository.OperationRepository;
import com.goduu.stocksstudies.services.exception.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import yahoofinance.histquotes.HistoricalQuote;

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


	@Autowired
	private StockDataService stockDataService;

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
		if (validateOperation(obj)) {
			return repo.insert(obj);
		} else {
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

	public PortifolioDTO getCurrentPortifolio(String userId) throws IOException {

		List<Operation> operations = findAllByUserId(userId);

		Map<String, List<Operation>> operationsGrouped = operations.stream()
				.collect(Collectors.groupingBy(Operation::getAsset));

		PortifolioDTO summerizedPortifolio = new PortifolioDTO();

		for (Map.Entry<String, List<Operation>> entry : operationsGrouped.entrySet()) {
			Double sum = entry.getValue().stream().mapToDouble(Operation::getShares).sum();
			if (sum > 0) {

				PortifolioElement summerized = stockDataService.getPortifolioInfo(entry.getKey());
				summerized.setAsset(entry.getKey());
				summerized.setQuantity(sum);
				summerized.setTotalValue(sum * summerized.getCurrentPrice());
				summerizedPortifolio.getPortifolio().add(summerized);

			}
		}
		summerizedPortifolio.setSectors();
		summerizedPortifolio.setIndustries();

		return summerizedPortifolio;
	}

	public List<HistoricalQuote> getCurrentPortifolioHistorical(String userId) throws IOException {

		List<Operation> operations = findAllByUserId(userId);

		Map<String, List<Operation>> operationsGrouped = operations.stream()
				.collect(Collectors.groupingBy(Operation::getAsset));

		operations.stream().sorted((o1, o2) -> o1.getDate() < o2.getDate() ? 1 : -1).collect(Collectors.toList());

		Map<String, List<HistoricalQuote>> historical = stockDataService.getPriceHistoryByOperations(operations);

		List<HistoricalQuote> merged = new ArrayList<>();
		TimeseriesDTO tDto = new TimeseriesDTO();
		Boolean firsttime = true;

		for (Map.Entry<String, List<HistoricalQuote>> entry : historical.entrySet()) {
			if (firsttime) {
				tDto = new TimeseriesDTO(entry.getValue());
				firsttime = false;
			} else {
				TimeseriesDTO tsToMerge = new TimeseriesDTO(entry.getValue());
				merged = tDto.merge(tsToMerge, operationsGrouped);
				tDto = new TimeseriesDTO(merged);
			}
		}

		// getQuantityBeforeDate(operations);
		// summerizedPortifolio.setSectors();

		return merged;
	}



}
