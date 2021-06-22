package com.goduu.stocksstudies.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.goduu.stocksstudies.dto.ChartDataDTO;
import com.goduu.stocksstudies.dto.EsgDTO;
import com.goduu.stocksstudies.dto.PortifolioElement;
import com.goduu.stocksstudies.dto.TimeseriesDTO;
import com.goduu.stocksstudies.models.Operation;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
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

        public Map<String, Object> getStats(String ticker) throws IOException {

                Stock stock = YahooFinance.get(ticker);

                Map<String, Object> res = new HashMap<>();
                Map<String, Object> item = new HashMap<>();
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

        public JsonObject querySummary(String module, String ticker) throws JsonIOException, JsonSyntaxException, IOException{
                JsonObject summary = getJsonFromURL("https://query2.finance.yahoo.com/v10/finance/quoteSummary/"
                                + ticker + "?modules=" + module);
                JsonObject qs = summary.getAsJsonObject("quoteSummary");
                return qs.get("result").getAsJsonArray().get(0).getAsJsonObject().get(module)
                                .getAsJsonObject();      

        }

        public EsgDTO getEsg(String ticker) throws IOException {

                EsgDTO res = new EsgDTO();                
                JsonObject results = querySummary("esgScores", ticker);
                res.setPerformance(results.get("esgPerformance") != null ? results.get("esgPerformance").getAsString() : "");
                res.setValue(results.get("totalEsg").getAsJsonObject().get("raw").getAsDouble());
                res.addScores(results);
                return res;

        }

        public Map<String, BigDecimal> getStocksPrices(String[] tickers) throws IOException {
                Map<String, BigDecimal> res = new HashMap<>();
                Map<String, Stock> stocks = YahooFinance.get(tickers);
                for (Map.Entry<String, Stock> entry : stocks.entrySet()) {
                        res.put(entry.getKey(), entry.getValue().getQuote().getBid());
                }
                return res;
        }

        // public Map<String,Object> getStocksCurves(String[] tickers) throws
        // IOException{
        // Map<String,HistoricalQuote> res = new HashMap<>();
        // Map<String, Stock> stocks = YahooFinance.get(tickers, true);

        // Calendar from = Calendar.getInstance();
        // Calendar to = Calendar.getInstance();
        // from.add(Calendar.YEAR, -5);

        // for (Map.Entry<String, Stock> entry : stocks.entrySet()) {
        // res.put(entry.getKey(), entry.getValue().getHistory());
        // }
        // return res;
        // }

        public JsonObject getJsonFromURL(String sUrl) throws JsonIOException, JsonSyntaxException, IOException {
                // Connect to the URL using java's native library
                URL url = new URL(sUrl);
                URLConnection request = url.openConnection();
                request.connect();

                // Convert to a JSON object to print data
                JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent())); // Convert
                                                                                                                      // the
                                                                                                                      // input
                                                                                                                      // stream
                                                                                                                      // to
                                                                                                                      // a
                                                                                                                      // json
                                                                                                                      // element
                JsonObject rootobj = root.getAsJsonObject(); // May be an array, may be an object.
                return rootobj;
        }

        public Map<String, Object> getData(String ticker) throws IOException {
                // public Map<String,Object> getData(String ticker) throws IOException{

                Map<String, Object> res = new HashMap<>();
                // Stock stock = YahooFinance.get(ticker);
                JsonObject summary = getJsonFromURL("https://query2.finance.yahoo.com/v10/finance/quoteSummary/"
                                + ticker + "?modules=summaryProfile");
                JsonObject qs = summary.getAsJsonObject("quoteSummary");
                JsonObject results = qs.get("result").getAsJsonArray().get(0).getAsJsonObject().get("summaryProfile")
                                .getAsJsonObject();
                res.put("website", results.get("website") != null ? results.get("website").getAsString() : "");
                res.put("industry", results.get("industry") != null ? results.get("industry").getAsString() : "");
                res.put("sector", results.get("sector") != null ? results.get("sector").getAsString() : "");
                res.put("longBusinessSummary",
                                results.get("longBusinessSummary") != null
                                                ? results.get("longBusinessSummary").getAsString()
                                                : "");
                summary = getJsonFromURL("https://query1.finance.yahoo.com/v7/finance/quote?symbols=" + ticker);
                results = summary.getAsJsonObject("quoteResponse").get("result").getAsJsonArray().get(0)
                                .getAsJsonObject();
                res.put("fullExchangeName",
                                results.get("fullExchangeName") != null ? results.get("fullExchangeName").getAsString()
                                                : "");
                res.put("currency", results.get("currency") != null ? results.get("currency").getAsString() : "");
                res.put("longName", results.get("longName") != null ? results.get("longName").getAsString() : "");
                res.put("price", results.get("regularMarketPrice") != null
                                ? results.get("regularMarketPrice").getAsBigDecimal()
                                : "");

                return res;

        }

        /*
         * Given a ticker Return its current Price and Sector
         */
        public PortifolioElement getPortifolioInfo(String ticker) throws IOException {
                // public Map<String,Object> getData(String ticker) throws IOException{

                PortifolioElement el = new PortifolioElement();
                JsonObject summary = getJsonFromURL("https://query2.finance.yahoo.com/v10/finance/quoteSummary/"
                                + ticker + "?modules=summaryProfile");
                JsonObject qs = summary.getAsJsonObject("quoteSummary");
                JsonObject results = qs.get("result").getAsJsonArray().get(0).getAsJsonObject().get("summaryProfile")
                                .getAsJsonObject();
                el.setCurrentPrice(YahooFinance.get(ticker).getQuote().getPrice().doubleValue());
                el.setSector(results.get("sector") != null ? results.get("sector").getAsString() : "");
                el.setIndustry(results.get("industry") != null ? results.get("industry").getAsString() : "");
                return el;

        }

        public Map<String, Object> getPriceHistory(ChartDataDTO objDto) throws IOException {

                Stock stock = YahooFinance.get(objDto.getTicker());

                Map<String, Object> res = new HashMap<>();

                Interval calendarGranularity = ((objDto.getGranularity().equals("MONTLY")) ? MONTHLY
                                : (objDto.getGranularity().equals("DAILY")) ? DAILY : WEEKLY);
                int calendarPeriod = ((objDto.getPeriod().equals("MONTH")) ? MONTH
                                : (objDto.getPeriod().equals("DAY")) ? DAY : YEAR);
                Calendar to = Calendar.getInstance();
                Calendar from = Calendar.getInstance();
                from.add(calendarPeriod, -objDto.getAmount());

                res.put("PriceHistory", stock.getHistory(from, to, calendarGranularity));
                res.put("type", "price");

                return res;

        }
        
        // public Map<String, Object> getEarningHistory(String ticker) throws IOException {

        //         Map<String, Object> res = new HashMap<>();

        //         JsonObject summary = getJsonFromURL("https://query2.finance.yahoo.com/v10/finance/quoteSummary/"
        //                         + ticker + "?modules=summaryProfile");
        //         JsonObject qs = summary.getAsJsonObject("quoteSummary");

        //         res.put("EarningHistory", stock.get(from, to, calendarGranularity));
        //         res.put("type", "price");

        //         return res;

        // }

        public Map<String, List<HistoricalQuote>> getPriceHistoryByOperations(List<Operation> op) throws IOException {

                Map<String, Long> firstDates = new HashMap<>();
                List<String> tickers = new ArrayList<>();
                Map<String, List<HistoricalQuote>> res = new HashMap<>();
                op.forEach(o -> {
                        tickers.add(o.getAsset());
                        if (firstDates.containsKey(o.getAsset())) {
                                if (firstDates.get(o.getAsset()) > o.getDate()) {
                                        firstDates.put(o.getAsset(), o.getDate());
                                }
                        } else {
                                firstDates.put(o.getAsset(), o.getDate());
                        }
                });
                tickers.stream().distinct().collect(Collectors.toList());
                Map<String, Stock> stocks = YahooFinance.get(tickers.toArray(new String[0]), true);

                Calendar from = Calendar.getInstance();
                Calendar to = Calendar.getInstance();
                from.add(Calendar.YEAR, -1); // from 1 year ago
                tickers.forEach(t -> {
                        from.setTimeInMillis(firstDates.get(t));
                        try {
                                res.put(t, stocks.get(t).getHistory(from, to, Interval.DAILY));
                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                });

                return res;

        }

        public Map<String, Object> getDividendHistory(ChartDataDTO objDto) throws IOException {

                Stock stock = YahooFinance.get(objDto.getTicker());

                Map<String, Object> res = new HashMap<>();

                int calendarPeriod = ((objDto.getPeriod().equals("MONTH")) ? MONTH
                                : (objDto.getPeriod().equals("DAY")) ? DAY : YEAR);
                Calendar to = Calendar.getInstance();
                Calendar from = Calendar.getInstance();
                from.add(calendarPeriod, -objDto.getAmount());

                res.put("PriceHistory", stock.getDividendHistory(from, to));
                res.put("type", "dividend");

                return res;

        }

}
