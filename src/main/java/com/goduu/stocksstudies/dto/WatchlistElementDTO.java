package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.util.List;

import com.goduu.stocksstudies.models.Ticker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistElementDTO implements Serializable{
    
    private ChartDTO priceChart;

    private Ticker ticker;
}
