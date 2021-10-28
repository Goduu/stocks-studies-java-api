package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.goduu.stocksstudies.dto.TickerDataChart.Dividend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TickerDataDividend implements Serializable {

    private List<Dividend> dividends = new ArrayList<>();

    public TickerDataDividend(TickerDataResponseDTO ticker) {

        Map<String, Dividend> dividendsMap = ticker.getChart().getResult().get(0).getEvents().getDividends();

        this.dividends.addAll(dividendsMap.values());

    }

}
