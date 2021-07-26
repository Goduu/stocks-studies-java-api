package com.goduu.stocksstudies.controllers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.goduu.stocksstudies.dto.ChartDTO;
import com.goduu.stocksstudies.dto.ChartDataDTO;
import com.goduu.stocksstudies.dto.EsgDTO;
import com.goduu.stocksstudies.dto.StatsDTO;
import com.goduu.stocksstudies.dto.StockDataDTO;
import com.goduu.stocksstudies.dto.WatchlistElementDTO;
import com.goduu.stocksstudies.models.Ticker;
import com.goduu.stocksstudies.services.StockDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.jsonwebtoken.io.IOException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/stocks")
public class StocksDataController {

	@Autowired
	private StockDataService service;

	@RequestMapping(value = "/data/{ticker}", method = RequestMethod.GET)
	public ResponseEntity<StockDataDTO> getData(@PathVariable String ticker)
			throws IOException, java.io.IOException {

		return ResponseEntity.ok().body(service.getData(ticker));

	}

	@RequestMapping(value = "/indic/{ticker}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getIndic(@PathVariable String ticker)
			throws IOException, java.io.IOException {

		return ResponseEntity.ok().body(service.getIndic(ticker));

	}
	
	@RequestMapping(value = "/stats/{ticker}", method = RequestMethod.GET)
	public ResponseEntity<List<StatsDTO>> getStats(@PathVariable String ticker)
			throws IOException, java.io.IOException {

		return ResponseEntity.ok().body(service.getStats(ticker));

	}
	
	@RequestMapping(value = "/esg/{ticker}", method = RequestMethod.GET)
	public ResponseEntity<EsgDTO> getEsg(@PathVariable String ticker)
			throws IOException, java.io.IOException {

		return ResponseEntity.ok().body(service.getEsg(ticker));

	}

	@RequestMapping(value = "/priceHistory", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getPriceHistory(@RequestBody ChartDataDTO objDto)
			throws IOException, java.io.IOException {

		return ResponseEntity.ok().body(service.getPriceHistory(objDto));

	}

	@RequestMapping(value = "/dividendHistory", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getDividendHistory(@RequestBody ChartDataDTO objDto)
			throws IOException, java.io.IOException {

		return ResponseEntity.ok().body(service.getDividendHistory(objDto));

	}

	@RequestMapping(value = "/getStocksPrices", method = RequestMethod.POST)
	public ResponseEntity<Map<String, BigDecimal>> getStocksPrices(@RequestBody String[] tickers)
			throws IOException, java.io.IOException {

		return ResponseEntity.ok().body(service.getStocksPrices(tickers));
	}
	@RequestMapping(value = "/getFinancialHistory/{ticker}", method = RequestMethod.GET)
	public ResponseEntity<ChartDTO> getFinancialHistory(@PathVariable String ticker)
			throws IOException, java.io.IOException, ParseException {

		return ResponseEntity.ok().body(service.getFinancialHistory(ticker));
	}

	@RequestMapping(value = "/getWatchlistData/{page}/{sortedBy}/{direction}", method = RequestMethod.POST)
	public ResponseEntity<List<Ticker>> getWatchlistData(
		@RequestBody List<String> tickers,
		@PathVariable int page,
		@PathVariable String sortedBy,
		@PathVariable String direction) {

		List<Ticker> list = service.getWatchlistData(tickers,page,sortedBy,direction);
		
		return ResponseEntity.ok().body(list);
	}
	

	@RequestMapping(value = "/testagg", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> testaggregationAPI(@RequestBody Map<String, Long> quantities) {

		List<String> tickers = new ArrayList<>();

		quantities.forEach((k, v) -> {
			tickers.add(k);
			System.out.println((k + ":" + v));
		}
		);
		String[] tickers_ = tickers.toArray(new String[0]);
		
		return null;
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Long>> testAPI(@RequestBody List<String> wordList)
			throws IOException, java.io.IOException {

		// Stock stock = YahooFinance.get(ticker);

		// Map<String,Object> res = new HashMap<>();
		// res.put("getDividend.getAnnualYield.Daily",
		// stock.getHistory(Interval.DAILY));
		// res.put("getDividend.getAnnualYield", stock.getDividend().getAnnualYield());
		// res.put("getDividend.getAnnualYieldPercent",
		// stock.getDividend().getAnnualYieldPercent());
		// res.put("getDividend.getPayDate", stock.getDividend().getPayDate());
		// res.put("getDividend.getExDate", stock.getDividend().getExDate());
		// res.put("getDividend.getSymbol", stock.getDividend().getSymbol());
		// res.put("getDividendHistory", stock.getDividendHistory().toArray());
		// res.put("getCurrency", stock.getCurrency());
		// res.put("getHistory", stock.getHistory().toArray());
		// res.put("getQuote.getPrice", stock.getQuote().getPrice());
		// res.put("getQuote.getVolume", stock.getQuote().getVolume());
		// res.put("getQuote.getAsk", stock.getQuote().getAsk());
		// res.put("getQuote.getAskSize", stock.getQuote().getAskSize());
		// res.put("getQuote.getAvgVolume", stock.getQuote().getAvgVolume());
		// res.put("getQuote.getBid", stock.getQuote().getBid());
		// res.put("getQuote.getBidSize", stock.getQuote().getBidSize());
		// res.put("getQuote.getChange", stock.getQuote().getChange());
		// res.put("getQuote.getChangeFromAvg200",
		// stock.getQuote().getChangeFromAvg200());
		// res.put("getQuote.getChangeFromAvg200InPercent",
		// stock.getQuote().getChangeFromAvg200InPercent());
		// res.put("getQuote.getChangeFromAvg50",
		// stock.getQuote().getChangeFromAvg50());
		// res.put("getQuote.getChangeFromAvg50InPercent",
		// stock.getQuote().getChangeFromAvg50InPercent());
		// res.put("getQuote.getChangeFromYearHigh",
		// stock.getQuote().getChangeFromYearHigh());
		// res.put("getQuote.getChangeFromYearHighInPercent",
		// stock.getQuote().getChangeFromYearHighInPercent());
		// res.put("getQuote.getChangeFromYearLow",
		// stock.getQuote().getChangeFromYearLow());
		// res.put("getQuote.getChangeFromYearLowInPercent",
		// stock.getQuote().getChangeFromYearLowInPercent());
		// res.put("getQuote.getChangeInPercent",
		// stock.getQuote().getChangeInPercent());
		// res.put("getQuote.getDayHigh", stock.getQuote().getDayHigh());
		// res.put("getQuote.getDayLow", stock.getQuote().getDayLow());
		// res.put("getQuote.getLastTradeDateStr",
		// stock.getQuote().getLastTradeDateStr());
		// res.put("getQuote.getLastTradeSize", stock.getQuote().getLastTradeSize());
		// res.put("getQuote.getLastTradeTime", stock.getQuote().getLastTradeTime());
		// res.put("getQuote.getOpen", stock.getQuote().getOpen());
		// res.put("getQuote.getPreviousClose", stock.getQuote().getPreviousClose());
		// res.put("getQuote.getPrice", stock.getQuote().getPrice());
		// res.put("getQuote.getPriceAvg200", stock.getQuote().getPriceAvg200());
		// res.put("getQuote.getPriceAvg50", stock.getQuote().getPriceAvg50());
		// res.put("getQuote.getSymbol", stock.getQuote().getSymbol());
		// res.put("getQuote.getTimeZone", stock.getQuote().getTimeZone());
		// res.put("getQuote.getVolume", stock.getQuote().getVolume());
		// res.put("getQuote.getYearHigh", stock.getQuote().getYearHigh());
		// res.put("getQuote.getYearLow", stock.getQuote().getYearLow());
		// res.put("getStockExchange", stock.getStockExchange());
		// res.put("getStats", stock.getStats());
		// res.put("getSplitHistory", stock.getSplitHistory());
		// return ResponseEntity.ok().body(res);
		return null;
	}

}
