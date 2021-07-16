package com.goduu.stocksstudies.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* Grid is the main entity we'll be using to save the grid elements
* 
* @author Goduu
* 
*/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "watchlist")
public class Watchlist implements Serializable{

	private static final long serialVersionUID = 1L;

    private String id;
    // @DBRef(lazy = true)
    @NotNull
	private String userId;

    private List<String> list = new ArrayList<>();
    
}

// @Getter
//     @Setter
//     @NoArgsConstructor
//     private class WatchlistItem implements Serializable{
        
//         Double eps;
//         Double beta;
//         String profitMargins;
//         String earningsQuarterlyGrowth;
//         Double bookValuePerShare;
//         Double priceBook;
//         Double lastDividendValue;
//         String lastDividendDate;

//     }
