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
public class TickerDataKeyStatistics implements Serializable {

    private RawLong enterpriseValue;
    private RawDouble forwardPE;
    private RawDouble profitMargins;
    private RawLong floatShares;
    private RawLong sharesOutstanding;
    private RawLong sharesShort;
    private RawLong sharesShortPriorMonth;
    private RawLong sharesShortPreviousMonthDate;
    private RawLong dateShortInterest;
    private RawDouble sharesPercentSharesOut;
    private RawDouble heldPercentInsiders;
    private RawDouble heldPercentInstitutions;
    private RawDouble shortRatio;
    private RawDouble shortPercentOfFloat;
    private RawDouble beta;
    private RawDouble bookValue;
    private RawDouble priceToBook;
    private RawLong lastFiscalYearEnd;
    private RawLong nextFiscalYearEnd;
    private RawLong mostRecentQuarter;
    private RawDouble earningsQuarterlyGrowth;
    private RawLong netIncomeToCommon;
    private RawDouble trailingEps;
    private RawDouble forwardEps;
    private RawDouble pegRatio;
    private String lastSplitFactor;
    private RawLong lastSplitDate;
    private RawDouble enterpriseToRevenue;
    private RawDouble enterpriseToEbitda;
    // private RawDouble change52Week;
    private RawDouble sandP52WeekChange;
    private RawDouble lastDividendValue;
    private RawLong lastDividendDate;

}
