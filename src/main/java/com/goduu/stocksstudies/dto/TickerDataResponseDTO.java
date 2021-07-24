package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TickerDataResponseDTO implements Serializable {

    private QuoteSummary quoteSummary;

    @Getter
    @Setter
    public static class QuoteSummary {
    
        private List<ResultItem> result;
    
    }
    
    @Getter
	@Setter
	public static class ResultItem {

		private TickerDataSummaryDetail summaryDetail;
        private TickerDataKeyStatistics defaultKeyStatistics;
        private TickerDataFinancialData financialData;
        private TickerDataSummaryProfile summaryProfile;

	}

    
}

