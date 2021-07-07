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
public class StatsDTO implements Serializable{

    String label;

    String dataType;

    Object value;
    
}
