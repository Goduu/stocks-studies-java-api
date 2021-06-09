package com.goduu.stocksstudies.dto;

import java.io.Serializable;

import com.goduu.stocksstudies.models.Ticker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TickerDTO implements Serializable{

    private String ticker;
    
    private String description;

    public TickerDTO(Ticker obj) {
      ticker = obj.getTicker();
		  description = obj.getDescription();
	}

    
}
