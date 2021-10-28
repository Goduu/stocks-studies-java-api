package com.goduu.stocksstudies.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.goduu.stocksstudies.dto.TickerDataChart;
import com.goduu.stocksstudies.dto.TickerDataDividend;
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
	private TickerDataChart chart = new TickerDataChart();
	private TickerDataDividend dividend = new TickerDataDividend();

	private Long keyStatisticsLastUpdate = 0L;
	private Long summaryDetailsLastUpdate = 0L;
	private Long financialDataLastUpdate = 0L;
	private Long summaryProfileLastUpdate = 0L;
	private Long chartLastUpdate = 0L;
	private Long dividendLastUpdate = 0L;

}
