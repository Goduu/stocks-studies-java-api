package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PriceDTO implements Serializable{

    String price;

    long timestamp;

    
}
