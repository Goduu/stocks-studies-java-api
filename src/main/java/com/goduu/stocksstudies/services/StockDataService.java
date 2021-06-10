package com.goduu.stocksstudies.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

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

    public JsonObject getJsonFromURL(String sUrl) throws JsonIOException, JsonSyntaxException, IOException{
            // Connect to the URL using java's native library
        URL url = new URL(sUrl);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
        return rootobj;
    }

    public Map<String,Object> getData(String ticker) throws IOException{
    // public Map<String,Object> getData(String ticker) throws IOException{
        
        Map<String,Object> res = new HashMap<>();
        // Stock stock = YahooFinance.get(ticker);
        JsonObject summary = getJsonFromURL("https://query2.finance.yahoo.com/v10/finance/quoteSummary/"+ticker+"?modules=summaryProfile");
        JsonObject qs = summary.getAsJsonObject("quoteSummary");
        JsonObject results = qs.get("result").getAsJsonArray().get(0).getAsJsonObject().get("summaryProfile").getAsJsonObject();
        res.put("website", results.get("website").getAsString());
        res.put("industry", results.get("industry").getAsString());
        res.put("sector", results.get("sector").getAsString());
        res.put("longBusinessSummary", results.get("longBusinessSummary").getAsString());
        summary = getJsonFromURL("https://query1.finance.yahoo.com/v7/finance/quote?symbols="+ticker);
        results = summary.getAsJsonObject("quoteResponse").get("result").getAsJsonArray().get(0).getAsJsonObject();
        res.put("fullExchangeName", results.get("fullExchangeName").getAsString());
        res.put("currency", results.get("currency").getAsString());
        res.put("longName", results.get("longName").getAsString());
        res.put("price", results.get("regularMarketPrice").getAsBigDecimal());

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
