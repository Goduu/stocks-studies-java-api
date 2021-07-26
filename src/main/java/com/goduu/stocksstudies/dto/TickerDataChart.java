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

        private List<Double> high;
        private List<Double> low;
        private List<Double> volume;
        private List<Double> open;
        private List<Double> close;

    }
}