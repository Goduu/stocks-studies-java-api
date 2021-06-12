package com.goduu.stocksstudies.models;

import java.io.Serializable;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ticker")
public class Ticker implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String displaySymbol;
	
	private String currency;
	
	private String description;

	private String exchange;
	
	@Indexed(unique = true)
	private String ticker;
	
	private String type;

}
