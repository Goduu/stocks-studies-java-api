package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.goduu.stocksstudies.models.Operation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yahoofinance.histquotes.HistoricalQuote;

@Getter
@Setter
@NoArgsConstructor
public class TimeseriesDTO implements Serializable {

    private SortedMap<Calendar, Double> eventValues = new TreeMap<>();

    private List<HistoricalQuote> eventList;

    private String ticker; 

    public TimeseriesDTO(List<HistoricalQuote> events) {
        if(!events.isEmpty()){
            events.forEach(e -> eventValues.put(e.getDate(), e.getClose().doubleValue()));
            eventList = new ArrayList<>(events);
            ticker = events.get(0).getSymbol();

        }
    }

    public List<HistoricalQuote> getEvents() {
        return Collections.unmodifiableList(eventList);
    }

    public Double getValueByDate(Calendar date) {
        Double value = eventValues.get(date);
        if (value == null) {
            value = 0d;
            // get values before the requested date
            // SortedMap<Calendar, Double> head = eventValues.headMap(date);
            // value = head.isEmpty() ? 0 // none before
            // : head.get(head.lastKey()); // first before
        }
        return value;
    }

    public List<HistoricalQuote> merge(TimeseriesDTO t, Map<String, List<Operation>> ops) {
        if (!t.getEventValues().isEmpty()) {

            List<HistoricalQuote> res = new ArrayList<>();

            Calendar maxDate = t.getEventValues().lastKey().before(eventValues.lastKey()) ? eventValues.lastKey()
                    : t.getEventValues().lastKey();
            Calendar minDate = t.getEventValues().firstKey().before(eventValues.firstKey())
                    ? t.getEventValues().firstKey()
                    : eventValues.firstKey();

            for (Calendar d = minDate; d.before(maxDate); d.add(Calendar.DATE, 1)) {
                Map<String, Double> qntityList = getQuantityBeforeDate(ops, d.getTimeInMillis());
                HistoricalQuote hc = new HistoricalQuote();
                Double value = qntityList.get(t.getTicker())* t.getValueByDate(d) +
                 (qntityList.get(getTicker()) == null ? 1 :  qntityList.get(getTicker())) *getValueByDate(d);
                if(value != 0){
                    hc.setClose(new BigDecimal(value, MathContext.DECIMAL64));
                    hc.setSymbol("Merged");
                    hc.setDate((Calendar) d.clone());
                    res.add(hc);
                }
            }
            return res;
        } else {
            return eventList;
        }

    }

    private Map<String, Double> getQuantityBeforeDate(Map<String, List<Operation>> list, long date) {

		Map<String, Double> res = new HashMap<>();

		for (Map.Entry<String, List<Operation>> entry : list.entrySet()) {

			Double sum = entry.getValue().stream().filter(v -> v.getDate() < date).mapToDouble(Operation::getShares).sum();
			res.put(entry.getKey(), sum);

		}

		return res;
	}

}
