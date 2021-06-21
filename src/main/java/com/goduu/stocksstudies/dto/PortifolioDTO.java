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
    List<PortifolioDivision> sectors = new ArrayList<>();
    
    @Getter
    List<PortifolioDivision> industries = new ArrayList<>();

    public void setSectors() {

        Map<String, Double> groupped = this.portifolio.stream()
                .sorted((el1, el2) -> el1.getSector().compareTo(el2.getSector()))
                .collect(Collectors.groupingBy(foo -> foo.sector, Collectors.summingDouble(foo -> foo.totalValue)));

        for (Map.Entry<String, Double> entry : groupped.entrySet()) {
            this.sectors.add(new PortifolioDivision(entry.getKey(), entry.getValue()));
        }
    }
    
    public void setIndustries() {

        Map<String, Double> groupped = this.portifolio.stream()
                .sorted((el1, el2) -> el1.getIndustry().compareTo(el2.getIndustry()))
                .collect(Collectors.groupingBy(foo -> foo.industry, Collectors.summingDouble(foo -> foo.totalValue)));

        for (Map.Entry<String, Double> entry : groupped.entrySet()) {
            this.industries.add(new PortifolioDivision(entry.getKey(), entry.getValue()));
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class PortifolioDivision implements Serializable {

        String name;

        Double totalValue;

    }

}
