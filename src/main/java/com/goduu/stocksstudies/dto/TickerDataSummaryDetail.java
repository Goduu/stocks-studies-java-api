package com.goduu.stocksstudies.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TickerDataSummaryDetail implements Serializable {

    private RawDouble dividendYield;
    private RawDouble payoutRatio;
    private RawDouble fiveYearAvgDividendYield;
    private RawDouble trailingPE;
    private RawLong volume;
    private RawLong averageDailyVolume10Day;
    private RawLong marketCap;
    private RawDouble fiftyTwoWeekLow;
    private RawDouble fiftyTwoWeekHigh;
    private RawDouble priceToSalesTrailing12Months;
    private RawDouble fiftyDayAverage;
    private RawDouble twoHundredDayAverage;
    private RawDouble trailingAnnualDividendRate;
    private RawDouble trailingAnnualDividendYield;

}
