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
public class TickerDataFinancialData implements Serializable {

    private RawDouble targetHighPrice;
    private RawDouble targetLowPrice;
    private RawDouble targetMeanPrice;
    private RawDouble targetMedianPrice;
    private RawDouble recommendationMean;
    private String recommendationKey;
    private RawLong numberOfAnalystOpinions;
    private RawLong totalCash;
    private RawDouble totalCashPerShare;
    private RawLong ebitda;
    private RawLong totalDebt;
    private RawDouble quickRatio;
    private RawDouble currentRatio;
    private RawLong totalRevenue;
    private RawDouble debtToEquity;
    private RawDouble revenuePerShare;
    private RawDouble returnOnAssets;
    private RawDouble returnOnEquity;
    private RawLong grossProfits;
    private RawLong freeCashflow;
    private RawLong operatingCashflow;
    private RawDouble earningsGrowth;
    private RawDouble revenueGrowth;
    private RawDouble grossMargins;
    private RawDouble ebitdaMargins;
    private RawDouble operatingMargins;
    private RawDouble profitMargins;

}
