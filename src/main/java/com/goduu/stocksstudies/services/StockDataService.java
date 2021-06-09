package com.goduu.stocksstudies.services;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

@Service
@Configuration
public class StockDataService {

    int MONTH = Calendar.MONTH;
    int DAY = Calendar.DATE;
    int YEAR = Calendar.YEAR;
    
    Interval MONTHLY = Interval.MONTHLY;
    Interval DAILY = Interval.DAILY;
    Interval WEEKLY = Interval.WEEKLY;

    public Map<String,Object> getStats(String ticker) throws IOException{
        
        Stock stock = YahooFinance.get(ticker);

        Map<String,Object> res = new HashMap<>();
        Map<String,Object> item = new HashMap<>();
        item.put("eps", stock.getStats().getEps());
        item.put("pe", stock.getStats().getPe());
        item.put("peg", stock.getStats().getPeg());
        item.put("roe", stock.getStats().getROE());
        item.put("ebitda", stock.getStats().getEBITDA());
        item.put("epsEstimateCurrentYear", stock.getStats().getEpsEstimateCurrentYear());
        item.put("epsEstimateNextQuarter", stock.getStats().getEpsEstimateNextQuarter());
        item.put("epsEstimateNextYear", stock.getStats().getEpsEstimateCurrentYear());
        res.put("indicators", item);
        item.clear();
        item.put("marketCap", stock.getStats().getMarketCap());
        item.put("sharesOutstanding", stock.getStats().getSharesOutstanding());
        item.put("priceBook", stock.getStats().getPriceBook());
        item.put("bookValuePerShare", stock.getStats().getBookValuePerShare());
        item.put("priceSales", stock.getStats().getPriceSales());
        item.put("revenue", stock.getStats().getRevenue());
        item.put("oneYearTargetPrice", stock.getStats().getOneYearTargetPrice());
        item.put("earningsAnnouncement", stock.getStats().getEarningsAnnouncement());
        res.put("market", item);


        return res;

    }

    public Map<String,Object> getData(String ticker) throws IOException{
        
        Stock stock = YahooFinance.get(ticker);

        Map<String,Object> res = new HashMap<>();

        res.put("currency", stock.getCurrency());
        res.put("price", stock.getQuote().getPrice());
        res.put("name", stock.getName());
        res.put("exchange", stock.getStockExchange());

        return res;

    }

    public Map<String,Object> getPriceHistory(String ticker, int amount, String period, String granularity) throws IOException{
        
        Stock stock = YahooFinance.get(ticker);

        Map<String,Object> res = new HashMap<>();

        Interval calendarGranularity = ((granularity.equals("MONTLY")) ? MONTHLY : (granularity.equals("DAILY")) ? DAILY : WEEKLY);
        int calendarPeriod = ((period.equals("MONTH")) ? MONTH : (period.equals("DAY")) ? DAY : YEAR);
        Calendar to = Calendar.getInstance();
        Calendar from = Calendar.getInstance();
        from.add(calendarPeriod, -amount);

        res.put("PriceHistory", stock.getHistory(from, to, calendarGranularity));

        return res;

    }

    public Map<String,Object> getDividendHistory(String ticker, int amount, String period) throws IOException{
        
        Stock stock = YahooFinance.get(ticker);

        Map<String,Object> res = new HashMap<>();

        int calendarPeriod = ((period.equals("MONTH")) ? MONTH : (period.equals("DAY")) ? DAY : YEAR);
        Calendar to = Calendar.getInstance();
        Calendar from = Calendar.getInstance();
        from.add(calendarPeriod, -amount);

        res.put("PriceHistory", stock.getDividendHistory(from, to));

        return res;

    }
    
}
