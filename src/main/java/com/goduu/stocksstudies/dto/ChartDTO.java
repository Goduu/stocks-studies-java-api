package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChartDTO implements Serializable{

    String type;

    List<Object> values = new ArrayList<>();

    public ChartDTO(String type){
        this.type = type;
    }

    public void setValues(List<Map<String, Object>> priceChart) {
    }
    
}
