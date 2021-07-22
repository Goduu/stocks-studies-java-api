package com.goduu.stocksstudies.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.goduu.stocksstudies.dto.TickerDTO;
import com.goduu.stocksstudies.models.Ticker;
import com.goduu.stocksstudies.repository.TickerRepository;
import com.goduu.stocksstudies.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class TickerService {

	@Autowired
	private TickerRepository repo;

	@Autowired
	private StockDataService dataService;

	@Autowired
	private Utils utils;

	public List<Ticker> findAllByDescriptionAndTickerAndExchange(String search, List<String> exchange, int pageSize) {

		Pageable pageable = PageRequest.of(0, pageSize);

		return repo.findAllByDescriptionAndTickerAndExchange(search, exchange, pageable).getContent();
	}

	public List<Ticker> fetchTickersBySearch(String search, int pageSize) {

		Pageable pageable = PageRequest.of(0, pageSize);

		return repo.fetchTickersBySearch(search, pageable).getContent();
	}

	public List<Ticker> fetchTickersInfosByList(List<String> tickersList, int pageSize, String sortedBy, int page) {

		Pageable pageable = PageRequest.of(page, pageSize,Sort.by(Sort.Direction.ASC, sortedBy));
		// Pageable pageable = PageRequest.of(page, pageSize,Sort.by(Sort.Direction.ASC, "keyStatistics.enterpriseValue"));
		

		List<Ticker> list = repo.fetchTickersInfosByList(tickersList, pageable).getContent();

		list = list.stream().map(el -> {
			return checkUpdateDates(el);

		}).collect(Collectors.toList());
		return list;
	}

	public Ticker fetchTickersInfos(String tickerString) {

		Ticker ticker = repo.findByTicker(tickerString);
		ticker = checkUpdateDates(ticker);

		return ticker;
	}

	private Ticker checkUpdateDates(Ticker ticker) {
		Long now = new Date().getTime();
		Long oneDay = 86400000L;
		if (now - ticker.getKeyStatisticsLastUpdate() > 3 * oneDay) {
			try {
				ticker = updateTickerKeyStatistics(ticker);
			} catch (JsonIOException | JsonSyntaxException | io.jsonwebtoken.io.IOException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (now - ticker.getFinancialDataLastUpdate() > oneDay) {
			try {
				ticker = updateTickerFinancialData(ticker);
			} catch (JsonIOException | JsonSyntaxException | io.jsonwebtoken.io.IOException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (now - ticker.getSummaryDetailsLastUpdate() > 3 * oneDay) {
			try {
				ticker = updateTickerSummaryDetails(ticker);
			} catch (JsonIOException | JsonSyntaxException | io.jsonwebtoken.io.IOException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ticker;

	}

	private Ticker updateTickerKeyStatistics(Ticker ticker)
			throws JsonIOException, JsonSyntaxException, io.jsonwebtoken.io.IOException, IOException {

		ticker = dataService.updateTickerKeyStatisticsInfos(ticker);
		return repo.save(ticker);

	}

	private Ticker updateTickerFinancialData(Ticker ticker)
			throws JsonIOException, JsonSyntaxException, io.jsonwebtoken.io.IOException, IOException {

		ticker = dataService.updateTickerFinancialDataInfos(ticker);
		return repo.save(ticker);

	}

	private Ticker updateTickerSummaryDetails(Ticker ticker)
			throws JsonIOException, JsonSyntaxException, io.jsonwebtoken.io.IOException, IOException {

		ticker = dataService.updateTickerSummaryDetailsInfos(ticker);
		return repo.save(ticker);

	}

	/**
	 * Find up to 10 Trending stocks
	 * 
	 * @param exchange name of the country exchange ('US', 'FR', 'DE'...)
	 * @return List of trending tickers with respective price data for one day
	 * @throws JsonIOException
	 * @throws JsonSyntaxException
	 * @throws IOException
	 */
	public List<TickerDTO> findTreddingByCountry(String exchange)
			throws JsonIOException, JsonSyntaxException, IOException {

		JsonArray trending = queryTrending(exchange);
		List<TickerDTO> tickers = new ArrayList<>();
		trending.forEach(t -> {
			TickerDTO ticker = new TickerDTO();
			String s = t.getAsJsonObject().get("symbol").getAsString();
			Ticker tickerDB = repo.findByTicker(s);
			if (tickerDB != null) {
				ticker.setDescription(tickerDB.getDescription());

				JsonObject financial = new JsonObject();
				try {
					financial = dataService.queryFinancial(s, "1d", "30m");
				} catch (JsonIOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ticker.setTicker(financial.get("meta").getAsJsonObject().get("symbol").getAsString());
				List<Object> priceChart = new ArrayList<>();
				int indx = 0;
				JsonArray dates = financial.get("timestamp").getAsJsonArray();
				JsonArray prices = financial.get("indicators").getAsJsonObject().get("quote").getAsJsonArray().get(0)
						.getAsJsonObject().get("close").getAsJsonArray();
				IntStream.range(0, prices.size()).forEach(idx -> {
					Map<String, Object> el = new HashMap<>();

					el.put("value", prices.get(idx));
					el.put("date", dates.get(indx));
					priceChart.add(el);
					// ticker.getPriceChart().getValues().add(value.getAsBigDecimal());
				});
				ticker.getPriceChart().setValues(priceChart);
				tickers.add(ticker);
			}
		});

		return tickers;
	}

	/**
	 * 
	 * @param exchange name of the country exchange ('US', 'FR', 'DE'...)
	 * @return a Json array with the cotes
	 * @throws JsonIOException
	 * @throws JsonSyntaxException
	 * @throws IOException
	 */
	public JsonArray queryTrending(String exchange) throws JsonIOException, JsonSyntaxException, IOException {
		JsonObject summary = utils
				.getJsonFromURL("https://query1.finance.yahoo.com/v1/finance/trending/" + exchange + "?count=10");
		JsonObject qs = summary.getAsJsonObject("finance");
		return qs.get("result").getAsJsonArray().get(0).getAsJsonObject().get("quotes").getAsJsonArray();

	}

}
