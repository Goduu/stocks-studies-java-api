package com.goduu.stocksstudies.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TickerDataChart {

    private List<Long> timestamp = new ArrayList<>();
    private ChartIndicators indicators = new ChartIndicators();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ChartIndicators {

        private List<Quote> quote = new ArrayList<>();

    }

    @Getter
    @Setter
    public static class Quote {

        private List<Double> high = new ArrayList<>();
        private List<Double> low = new ArrayList<>();
        private List<Double> volume = new ArrayList<>();
        private List<Double> open = new ArrayList<>();
        private List<Double> close = new ArrayList<>();

    }
}