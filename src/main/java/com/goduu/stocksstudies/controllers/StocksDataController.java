package com.goduu.stocksstudies.controllers;

import java.util.HashMap;
import java.util.Map;

import com.goduu.stocksstudies.services.StockDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.io.IOException;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

@RestController
@RequestMapping(value = "/api/stocks")
public class StocksDataController {

    @Autowired
    private StockDataService service;
	
	@RequestMapping(value = "/data/{ticker}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getData(@PathVariable String ticker) throws IOException, java.io.IOException {

		return ResponseEntity.ok().body(service.getData(ticker));

	}
	
	@RequestMapping(value = "/stats/{ticker}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getStats(@PathVariable String ticker) throws IOException, java.io.IOException {

		return ResponseEntity.ok().body(service.getStats(ticker));

	}
    
    @RequestMapping(value = "/priceHistory/{ticker}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getPriceHistory(@PathVariable String ticker) throws IOException, java.io.IOException {

        return ResponseEntity.ok().body(service.getPriceHistory(ticker, 365, "DAY", "WEEKLY"));
		
    }
    
	@RequestMapping(value = "/dividendHistory/{ticker}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getDividendHistory(@PathVariable String ticker) throws IOException, java.io.IOException {

        return ResponseEntity.ok().body(service.getDividendHistory(ticker, 365, "DAY"));

    }


    @RequestMapping(value = "/test/{ticker}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> testAPI(@PathVariable String ticker) throws IOException, java.io.IOException {
		
		Stock stock = YahooFinance.get(ticker);
		
		
		Map<String,Object> res = new HashMap<>();
		res.put("getDividend.getAnnualYield.Daily", stock.getHistory(Interval.DAILY));
		res.put("getDividend.getAnnualYield", stock.getDividend().getAnnualYield());
		res.put("getDividend.getAnnualYieldPercent", stock.getDividend().getAnnualYieldPercent());
		res.put("getDividend.getPayDate", stock.getDividend().getPayDate());
		res.put("getDividend.getExDate", stock.getDividend().getExDate());
		res.put("getDividend.getSymbol", stock.getDividend().getSymbol());
		res.put("getDividendHistory", stock.getDividendHistory().toArray());
		res.put("getCurrency", stock.getCurrency());
		res.put("getHistory", stock.getHistory().toArray());
		res.put("getQuote.getPrice", stock.getQuote().getPrice());
		res.put("getQuote.getVolume", stock.getQuote().getVolume());
		res.put("getQuote.getAsk", stock.getQuote().getAsk());
		res.put("getQuote.getAskSize", stock.getQuote().getAskSize());
		res.put("getQuote.getAvgVolume", stock.getQuote().getAvgVolume());
		res.put("getQuote.getBid", stock.getQuote().getBid());
		res.put("getQuote.getBidSize", stock.getQuote().getBidSize());
		res.put("getQuote.getChange", stock.getQuote().getChange());
		res.put("getQuote.getChangeFromAvg200", stock.getQuote().getChangeFromAvg200());
		res.put("getQuote.getChangeFromAvg200InPercent", stock.getQuote().getChangeFromAvg200InPercent());
		res.put("getQuote.getChangeFromAvg50", stock.getQuote().getChangeFromAvg50());
		res.put("getQuote.getChangeFromAvg50InPercent", stock.getQuote().getChangeFromAvg50InPercent());
		res.put("getQuote.getChangeFromYearHigh", stock.getQuote().getChangeFromYearHigh());
		res.put("getQuote.getChangeFromYearHighInPercent", stock.getQuote().getChangeFromYearHighInPercent());
		res.put("getQuote.getChangeFromYearLow", stock.getQuote().getChangeFromYearLow());
		res.put("getQuote.getChangeFromYearLowInPercent", stock.getQuote().getChangeFromYearLowInPercent());
		res.put("getQuote.getChangeInPercent", stock.getQuote().getChangeInPercent());
		res.put("getQuote.getDayHigh", stock.getQuote().getDayHigh());
		res.put("getQuote.getDayLow", stock.getQuote().getDayLow());
		res.put("getQuote.getLastTradeDateStr", stock.getQuote().getLastTradeDateStr());
		res.put("getQuote.getLastTradeSize", stock.getQuote().getLastTradeSize());
		res.put("getQuote.getLastTradeTime", stock.getQuote().getLastTradeTime());
		res.put("getQuote.getOpen", stock.getQuote().getOpen());
		res.put("getQuote.getPreviousClose", stock.getQuote().getPreviousClose());
		res.put("getQuote.getPrice", stock.getQuote().getPrice());
		res.put("getQuote.getPriceAvg200", stock.getQuote().getPriceAvg200());
		res.put("getQuote.getPriceAvg50", stock.getQuote().getPriceAvg50());
		res.put("getQuote.getSymbol", stock.getQuote().getSymbol());
		res.put("getQuote.getTimeZone", stock.getQuote().getTimeZone());
		res.put("getQuote.getVolume", stock.getQuote().getVolume());
		res.put("getQuote.getYearHigh", stock.getQuote().getYearHigh());
		res.put("getQuote.getYearLow", stock.getQuote().getYearLow());
		res.put("getStockExchange", stock.getStockExchange());
		res.put("getStats", stock.getStats());
		res.put("getSplitHistory", stock.getSplitHistory());
		return ResponseEntity.ok().body(res);
	}
    
}
