package com.goduu.stocksstudies.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.goduu.stocksstudies.dto.ChartDTO;
import com.goduu.stocksstudies.dto.ChartDataDTO;
import com.goduu.stocksstudies.dto.EsgDTO;
import com.goduu.stocksstudies.dto.FinancialDTO;
import com.goduu.stocksstudies.dto.PortifolioElement;
import com.goduu.stocksstudies.dto.StatsDTO;
import com.goduu.stocksstudies.models.Operation;
import com.goduu.stocksstudies.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
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

        @Autowired
        private Utils utils;

        public Map<String, Object> getIndic(String ticker) throws IOException {

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

        /**
         *  Get statistics from a ticker
         * @param ticker string ("AMZN", "WEGE3.SA"...)
         * @return A list with the statistics ({label, datatype("number", "date", "string"), value})
         * @throws IOException
         */
        public List<StatsDTO> getStats(String ticker) throws IOException {

                Stock stock = YahooFinance.get(ticker);

                List<StatsDTO> res = new ArrayList<>();
                StatsDTO item = new StatsDTO();
                item.setLabel("eps");
                item.setValue(stock.getStats().getEps());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("pe");
                item.setValue(stock.getStats().getPe());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("peg");
                item.setValue(stock.getStats().getPeg());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("roe");
                item.setValue(stock.getStats().getROE());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("ebitda");
                item.setValue(stock.getStats().getEBITDA());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("epsEstimateCurrentYear");
                item.setValue(stock.getStats().getEpsEstimateCurrentYear());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("epsEstimateNextQuarter");
                item.setValue(stock.getStats().getEpsEstimateNextQuarter());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("epsEstimateNextYear");
                item.setValue(stock.getStats().getEpsEstimateCurrentYear());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("marketCap");
                item.setValue(stock.getStats().getMarketCap());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("sharesOutstanding");
                item.setValue(stock.getStats().getSharesOutstanding());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("priceBook");
                item.setValue(stock.getStats().getPriceBook());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("bookValuePerShare");
                item.setValue(stock.getStats().getBookValuePerShare());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("priceSales");
                item.setValue(stock.getStats().getPriceSales());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("revenue");
                item.setValue(stock.getStats().getRevenue());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                item.setLabel("oneYearTargetPrice");
                item.setValue(stock.getStats().getOneYearTargetPrice());
                item.setDataType("number");
                res.add(item);
                item = new StatsDTO();
                // item.setLabel("earningsAnnouncement");
                // item.setValue(stock.getStats().getEarningsAnnouncement());
                // item.setDataType("date");
                // res.add(item);
                res = res.stream().filter(r -> r.getValue() != null).collect(Collectors.toList());
                return res;

        }

        /**
         * Return the results from a ticker from a respective module
         * @param module the type o information to be quered
         * Examples of modules: 'assetProfile','summaryProfile','summaryDetail','esgScores','price','incomeStatementHistory','incomeStatementHistoryQuarterly',
         * 'balanceSheetHistory','balanceSheetHistoryQuarterly','cashflowStatementHistory','cashflowStatementHistoryQuarterly','defaultKeyStatistics','financialData',
         * 'calendarEvents','secFilings','recommendationTrend','upgradeDowngradeHistory','institutionOwnership','fundOwnership','majorDirectHolders','majorHoldersBreakdown',
         * 'insiderTransactions','insiderHolders','netSharePurchaseActivity','earnings','earningsHistory','earningsTrend','industryTrend','indexTrend','sectorTrend'
         * @param ticker
         * @return The JsonObject of the respectiv module
         * @throws JsonIOException
         * @throws JsonSyntaxException
         * @throws IOException
         */
        public JsonObject querySummary(String module, String ticker)
                        throws JsonIOException, JsonSyntaxException, IOException {
                JsonObject summary = utils.getJsonFromURL("https://query2.finance.yahoo.com/v10/finance/quoteSummary/"
                                + ticker + "?modules=" + module);
                JsonObject qs = summary.getAsJsonObject("quoteSummary");
                return qs.get("result").getAsJsonArray().get(0).getAsJsonObject().get(module).getAsJsonObject();

        }


        public ChartDTO getFinancialHistory(String ticker) throws IOException, ParseException {

                List<Object> res = new ArrayList<>();
                JsonObject results = querySummary("earnings", ticker);
                JsonObject financials = results.get("financialsChart").getAsJsonObject();
                JsonArray yearly = financials.get("yearly").getAsJsonArray();
                JsonArray quarterly = financials.get("quarterly").getAsJsonArray();
                // "3Q2020"
                // "2017"
                String patternYearly = "MM-yyyy";
                SimpleDateFormat df = new SimpleDateFormat(patternYearly);

                quarterly.forEach(q -> {
                        FinancialDTO fin = new FinancialDTO();
                        fin.setPeriod("quarterly");
                        fin.setType("revenue");
                        fin.setFormatedValue(
                                        q.getAsJsonObject().get("revenue").getAsJsonObject().get("fmt").getAsString());
                        fin.setValue(q.getAsJsonObject().get("revenue").getAsJsonObject().get("raw").getAsBigInteger());
                        try {
                                fin.setDateEpoch(utils.getDateFromQuarter(q.getAsJsonObject().get("date").getAsString()));
                        } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                        fin.setDate(q.getAsJsonObject().get("date").getAsString());
                        res.add(fin);
                });
                yearly.forEach(q -> {
                        FinancialDTO fin = new FinancialDTO();
                        fin.setPeriod("yearly");
                        fin.setType("revenue");
                        fin.setFormatedValue(
                                        q.getAsJsonObject().get("revenue").getAsJsonObject().get("fmt").getAsString());
                        fin.setValue(q.getAsJsonObject().get("revenue").getAsJsonObject().get("raw").getAsBigInteger());
                        try {
                                fin.setDateEpoch(df.parse("12-" + q.getAsJsonObject().get("date").getAsString())
                                                .getTime());
                        } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                        fin.setDate(q.getAsJsonObject().get("date").getAsString());
                        res.add(fin);
                });

                ChartDTO chart = new ChartDTO();
                chart.setType("financial");
                chart.setValues(res);
                return chart;

        }

        public EsgDTO getEsg(String ticker) throws IOException {

                EsgDTO res = new EsgDTO();
                JsonObject results = querySummary("esgScores", ticker);
                res.setPerformance(results.get("esgPerformance") != null ? results.get("esgPerformance").getAsString()
                                : "");
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

        public Map<String, Object> getData(String ticker) throws IOException {
                // public Map<String,Object> getData(String ticker) throws IOException{

                Map<String, Object> res = new HashMap<>();
                // Stock stock = YahooFinance.get(ticker);
                JsonObject summary = utils.getJsonFromURL("https://query2.finance.yahoo.com/v10/finance/quoteSummary/"
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
                summary = utils.getJsonFromURL("https://query1.finance.yahoo.com/v7/finance/quote?symbols=" + ticker);
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

        /**
         *  Given a ticker Return its current Price and Sector
         * @param ticker ticker string ("AMZN", "WEGE3.SA"...)
         * @return Price and sector of the tick
         * @throws IOException
         */
        public PortifolioElement getPortifolioInfo(String ticker) throws IOException {
                // public Map<String,Object> getData(String ticker) throws IOException{

                PortifolioElement el = new PortifolioElement();
                JsonObject summary = utils.getJsonFromURL("https://query2.finance.yahoo.com/v10/finance/quoteSummary/"
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
