package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class PortifolioDTO implements Serializable {

    @Getter
    @Setter
    List<PortifolioElement> portifolio = new ArrayList<>();

    @Getter
    List<PortifolioSector> sectors = new ArrayList<>();

    public void setSectors() {

        Map<String, Double> groupped = this.portifolio.stream()
                .sorted((el1, el2) -> el1.getSector().compareTo(el2.getSector()))
                .collect(Collectors.groupingBy(foo -> foo.sector, Collectors.summingDouble(foo -> foo.totalValue)));

        for (Map.Entry<String, Double> entry : groupped.entrySet()) {
            this.sectors.add(new PortifolioSector(entry.getKey(), entry.getValue()));
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class PortifolioSector implements Serializable {

        String sector;

        Double totalValue;

    }

}
