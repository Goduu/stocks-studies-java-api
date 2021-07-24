package com.goduu.stocksstudies.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.goduu.stocksstudies.dto.TickerDataFinancialData;
import com.goduu.stocksstudies.dto.TickerDataKeyStatistics;
import com.goduu.stocksstudies.dto.TickerDataSummaryDetail;
import com.goduu.stocksstudies.dto.TickerDataSummaryProfile;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ticker")
public class Ticker implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String displaySymbol;

	private String currency;

	private String description;

	private String exchange;

	@Indexed(unique = true)
	private String ticker;

	private String type;

	private Long accesses = 0L;

	private TickerDataKeyStatistics keyStatistics = new TickerDataKeyStatistics();
	private TickerDataSummaryDetail summaryDetail = new TickerDataSummaryDetail();
	private TickerDataFinancialData financialData = new TickerDataFinancialData();
	private TickerDataSummaryProfile summaryProfile = new TickerDataSummaryProfile();

	private Long keyStatisticsLastUpdate = 0L;
	private Long summaryDetailsLastUpdate = 0L;
	private Long financialDataLastUpdate = 0L;
	private Long summaryProfileLastUpdate = 0L;

	@Getter
	@Setter
	@NoArgsConstructor
	public class KeyStatistics implements Serializable {

		private Long enterpriseValue;
		private BigDecimal forwardPE;
		private BigDecimal profitMargins;
		private Long floatShares;
		private Long sharesOutstanding;
		private Long sharesShort;
		private Long sharesShortPriorMonth;
		private Long sharesShortPreviousMonthDate;
		private Long dateShortInterest;
		private BigDecimal sharesPercentSharesOut;
		private BigDecimal heldPercentInstitutions;
		private BigDecimal shortRatio;
		private BigDecimal shortPercentOfFloat;
		private BigDecimal beta;
		private BigDecimal bookValue;
		private BigDecimal priceToBook;
		private Long lastFiscalYearEnd;
		private Long nextFiscalYearEnd;
		private Long mostRecentQuarter;
		private BigDecimal earningsQuarterlyGrowth;
		private Long netIncomeToCommon;
		private BigDecimal trailingEps;
		private BigDecimal forwardEps;
		private BigDecimal pegRatio;
		private String lastSplitFactor;
		private Long lastSplitDate;
		private BigDecimal enterpriseToRevenue;
		private BigDecimal enterpriseToEbitda;
		private BigDecimal change52Week;
		private BigDecimal sandP52WeekChange;
		private BigDecimal lastDividendValue;
		private Long lastDividendDate;

	}

	// https://query2.finance.yahoo.com/v10/finance/quoteSummary/AAPL?modules=summaryDetail


	// https://query2.finance.yahoo.com/v10/finance/quoteSummary/AAPL?modules=financialData
	@Getter
	@Setter
	@NoArgsConstructor
	public class FinancialData implements Serializable {
		private BigDecimal targetHighPrice;
		private BigDecimal targetLowPrice;
		private BigDecimal targetMeanPrice;
		private BigDecimal targetMedianPrice;
		private BigDecimal recommendationMean;
		private String recommendationKey;
		private BigInteger numberOfAnalystOpinions;
		private Long totalCash;
		private BigDecimal totalCashPerShare;
		private Long ebitda;
		private Long totalDebt;
		private BigDecimal quickRatio;
		private BigDecimal currentRatio;
		private Long totalRevenue;
		private BigDecimal debtToEquity;
		private BigDecimal revenuePerShare;
		private BigDecimal returnOnAssets;
		private BigDecimal returnOnEquity;
		private Long grossProfits;
		private Long freeCashflow;
		private Long operatingCashflow;
		private BigDecimal earningsGrowth;
		private BigDecimal revenueGrowth;
		private BigDecimal grossMargins;
		private BigDecimal ebitdaMargins;
		private BigDecimal operatingMargins;
		private BigDecimal profitMargins;

	}

	@Getter
	@Setter
	@NoArgsConstructor
	public class SummaryProfile implements Serializable {
		private String industry;
		private String sector;
		private String country;
		private String website;

	}

}
