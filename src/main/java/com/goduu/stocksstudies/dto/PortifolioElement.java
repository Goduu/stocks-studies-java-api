package com.goduu.stocksstudies.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PortifolioElement implements Serializable {

    String asset;

    String sector;

    Double quantity;

    Double totalValue;

    Double currentPrice;

}
