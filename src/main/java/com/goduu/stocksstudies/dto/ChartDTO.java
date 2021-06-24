package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChartDTO implements Serializable{

    String type;

    List<Object> values;
    
}
