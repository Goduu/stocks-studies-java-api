package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDataDTO implements Serializable {

    String ticker;
    String website;
    String industry;
    String sector;
    String longBusinessSummary;
    String fullExchangeName;
    String currency;
    String longName;
    BigDecimal price;
}
