package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.util.List;

import com.goduu.stocksstudies.models.StockDataDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistElementDTO implements Serializable{
    
    private StockDataDTO data;

    private ChartDTO priceChart;

    private List<StatsDTO> statistics;
}
