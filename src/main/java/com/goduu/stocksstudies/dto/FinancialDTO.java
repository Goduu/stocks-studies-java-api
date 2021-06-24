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
public class FinancialDTO implements Serializable{

    String period; //'yearly' || 'quarterly'

    String date;
    
    long dateEpoch;

    String type; //earnings or revenue

    BigInteger value;

    String formatedValue;

    
}
